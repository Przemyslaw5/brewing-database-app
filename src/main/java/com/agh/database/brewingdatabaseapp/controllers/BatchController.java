package com.agh.database.brewingdatabaseapp.controllers;

import com.agh.database.brewingdatabaseapp.model.Batch;
import com.agh.database.brewingdatabaseapp.model.Log;
import com.agh.database.brewingdatabaseapp.services.BatchService;
import com.agh.database.brewingdatabaseapp.services.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class BatchController {

    private final BatchService batchService;
    private final IngredientService ingredientService;

    public BatchController(BatchService batchService, IngredientService ingredientService){
        this.batchService = batchService;
        this.ingredientService = ingredientService;
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
            matchingBatches = this.batchService.findByNameStartingWith("b");
        }
        else{
            System.out.println("BEFORE" + batch.getName());
            matchingBatches = this.batchService.findByNameStartingWith(batch.getName());
            System.out.println("AFTER" + batch.getName());
        }


        if (matchingBatches.isEmpty()) {
            result.rejectValue("name", "notFound", "There is no batch of beer with this name.");
            return "batches/findBatches";
        }
//        else if (results.size() == 1) {
//            // 1 owner found
//            owner = results.iterator().next();
//            return "redirect:/owners/" + owner.getId();
//        }
        else {
            model.put("matchingbatches", matchingBatches);
            return "batches/batchesList";
        }
    }

    @GetMapping("/batches/new")
    public String initCreationForm(Model model) {
        Batch batch = new Batch();
        Set<String> freezerNames = this.batchService.getUniqueFreezerNames();
        batch.setBatchIngredients(ingredientService.prepareBatchIngredientsList());
        model.addAttribute("batch", batch);
        model.addAttribute("freezers", freezerNames);
        return "batches/new";
    }

    @PostMapping("/batches/new")
    public String processCreationForm(@Valid Batch batch, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            System.out.println("NIEPOPRAWNE DANE?");
            Set<String> freezerNames = this.batchService.getUniqueFreezerNames();
            batch.setBatchIngredients(ingredientService.prepareBatchIngredientsList());
            model.addAttribute("freezers", freezerNames);
            return "batches/new";
        }
        else {
            batch.setBatchIngredients(ingredientService.getBatchIngredientsList(batch.getBatchIngredients(), batch));
            batch.setFreezer(batchService.getFreezerByName(batch.getFreezer().getName()));
            this.batchService.save(batch);
            return "redirect:/batches/" + batch.getName();
        }
    }

    @GetMapping("/batches/{batchName}")
    public ModelAndView showBatch(@PathVariable("batchName") String batchName) {
        ModelAndView mav = new ModelAndView("batches/batchDetails");
        Batch batch = this.batchService.findByName(batchName);

        mav.addObject(batch);
        return mav;
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
            batch.addLog(log);
            batchService.save(batch);
            return "redirect:/batches/" + batchName;
        }
    }
}
