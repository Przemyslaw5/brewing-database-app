package com.agh.database.brewingdatabaseapp.controllers;

import com.agh.database.brewingdatabaseapp.model.Batch;
import com.agh.database.brewingdatabaseapp.model.Log;
import com.agh.database.brewingdatabaseapp.model.Mash;
import com.agh.database.brewingdatabaseapp.services.BatchService;
import com.agh.database.brewingdatabaseapp.services.FreezerService;
import com.agh.database.brewingdatabaseapp.services.IngredientService;
import com.google.gson.JsonArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class BatchController {

    private final BatchService batchService;
    private final IngredientService ingredientService;
    private final FreezerService freezerService;

    public BatchController(BatchService batchService, IngredientService ingredientService, FreezerService freezerService){
        this.batchService = batchService;
        this.ingredientService = ingredientService;
        this.freezerService = freezerService;
    }

    @GetMapping("batches/find")
    public String initFindFrom(Map<String, Object> model){
        model.put("batch", new Batch());
        return "batches/findBatches";
    }

    @GetMapping("/batches")
    public String processFindForm(Batch batch, BindingResult result, Map<String, Object> model) {

        List<Batch> matchingBatches;

        if (batch.getName() == null) {
            matchingBatches = this.batchService.findByNameStartingWith("");
        }
        else{
            matchingBatches = this.batchService.findByNameStartingWith(batch.getName());
        }

        if (matchingBatches.isEmpty()) {
            result.rejectValue("name", "notFound", "There is no batch of beer with this name.");
            return "batches/findBatches";
        }
        else if (matchingBatches.size() == 1) {
            return "redirect:/batches/" + matchingBatches.get(0).getName();
        }
        else {
            model.put("matchingbatches", matchingBatches);
            return "batches/batchesList";
        }
    }

    @GetMapping("/batches/new")
    public String initCreationForm(Model model) {
        Batch batch = new Batch();
        Set<String> freezerNames = this.freezerService.getUniqueFreezerNames();
        ArrayList<Mash> mashes = new ArrayList<>();
        mashes.add(new Mash());
        batch.setMashes(mashes);
        batch.setBatchIngredients(ingredientService.prepareBatchIngredientsList());
        model.addAttribute("batch", batch);
        model.addAttribute("freezers", freezerNames);
        model.addAttribute("amountError", "");
        model.addAttribute("amountAvailable", ingredientService.getAmountAvailable(batch.getBatchIngredients()));
        return "batches/new";
    }

    @PostMapping("/batches/new")
    public String processCreationForm(@Valid Batch batch, BindingResult result, Model model) {
        Batch b = batchService.findByName(batch.getName());
        int flagDate = batch.getBottledDate().compareTo(batch.getBrewedDate());
        boolean flagAmounts = ingredientService.checkAmounts(batch.getBatchIngredients());
        if (result.hasErrors() || b != null || flagDate < 0 || flagAmounts) {
            String amountError = "";
            if (b != null) {
                FieldError nameError = new FieldError("name", "name", "Batch with that name already exists.");
                result.addError(nameError);
            }
            if(flagDate < 0){
                FieldError dateError = new FieldError("brewedDate", "brewedDate", "Beer is brewed before bottling.");
                result.addError(dateError);
            }
            if(flagAmounts){
                amountError = "Make sure there is such a quantity in stock";
            }
            Set<String> freezerNames = this.freezerService.getUniqueFreezerNames();
            ArrayList<Mash> mashes = new ArrayList<>();
            for(int i = 0; i < batch.getMashes().size(); i++){
                mashes.add(new Mash());
            }
            batch.setMashes(mashes);
            batch.setBatchIngredients(ingredientService.prepareBatchIngredientsList());
            model.addAttribute("amountError", amountError);
            model.addAttribute("freezers", freezerNames);
            model.addAttribute("amountAvailable", ingredientService.getAmountAvailable(batch.getBatchIngredients()));
            return "batches/new";
        }
        else {
            ingredientService.changeAmountsInStock(batch.getBatchIngredients());
            this.batchService.save(batch);
            batch = this.batchService.findByName(batch.getName());
            batch.setBatchIngredients(ingredientService.getBatchIngredientsList(batch.getBatchIngredients(), batch));
            batch.setMashes(batchService.prepareMashesFromInputToBatch(batch.getMashes()));
            batch.setFreezer(freezerService.findByName(batch.getFreezer().getName()));
            batch.setBottledDate(batch.getBottledDate().replace("T", " "));
            batch.setBrewedDate(batch.getBrewedDate().replace("T", " "));
            this.batchService.save(batch);
            return "redirect:/batches/" + batch.getName();
        }
    }

    @GetMapping("/batches/{batchName}")
    public String showBatch(@PathVariable("batchName") String batchName, Model model) {
        Batch batch = this.batchService.findByName(batchName);
        JsonArray jsonArrayAverageTemp = new JsonArray();
        JsonArray jsonArrayTempSet = new JsonArray();
        JsonArray jsonArrayData = new JsonArray();
        if(batch.getLogs() != null){
            batch.getLogs().forEach(log -> {
                jsonArrayData.add(log.getTime());
                jsonArrayAverageTemp.add(Math.round((log.getTemp_in() + log.getTemp_out()) * 50) / 100.0);
                jsonArrayTempSet.add(log.getTemp_set());
            });
        }
        model.addAttribute("batch", batch);
        model.addAttribute("jsonArrayData", jsonArrayData);
        model.addAttribute("jsonArrayAverageTemp", jsonArrayAverageTemp);
        model.addAttribute("jsonArrayTempSet", jsonArrayTempSet);
        return "batches/batchDetails";
    }

    @GetMapping("/batches/{batchName}/logs/new")
    public String addNewLogToBatch(Model model){

        Log log = new Log();
        model.addAttribute("log", log);

        return "logs/new";
    }

    @PostMapping("/batches/{batchName}/logs/new")
    public String addNewLogToBatch(@PathVariable("batchName") String batchName, @Valid Log log, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("log", log);
            return "logs/new";
        }
        else{
            Batch batch = batchService.findByName(batchName);
            log.setId();
            log.setTime(log.getTime().replace("T", " "));
            batch.addLog(log);
            batchService.save(batch);
            return "redirect:/batches/" + batchName;
        }
    }

    @GetMapping("/batches/{batchName}/logs/delete/{id}")
    public String deleteLogInBatch(@PathVariable("batchName") String batchName, @PathVariable("id") int id){

        this.batchService.deleteLog(batchName, id);

        return "redirect:/batches/" + batchName;
    }
}
