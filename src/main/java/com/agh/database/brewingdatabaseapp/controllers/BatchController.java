package com.agh.database.brewingdatabaseapp.controllers;

import com.agh.database.brewingdatabaseapp.model.Batch;
import com.agh.database.brewingdatabaseapp.services.BatchService;
import org.springframework.stereotype.Controller;
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

    public BatchController(BatchService batchService){
        this.batchService = batchService;
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
    public String initCreationForm(Map<String, Object> model) {
        Batch batch = new Batch();
        model.put("batch", batch);

        Set<String> freezersName = this.batchService.getUniqueFreezerNames();
        for (String s : freezersName) {
            System.out.println(s);
        }
        model.put("freezers", freezersName);

        return "batches/new";
    }

    @PostMapping("/batches/new")
    public String processCreationForm(@Valid Batch batch, BindingResult result, Map<String, Object> model) {
        if (result.hasErrors()) {

            Set<String> freezersName = this.batchService.getUniqueFreezerNames();
            for (String s : freezersName) {
                System.out.println(s);
            }
            model.put("freezers", freezersName);

            return "batches/new";
        }
        else {
            System.out.println("Poprawne dane");
            this.batchService.save(batch);
            return "batches/new";//return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/batches/{batchId}")
    public ModelAndView showBatch(@PathVariable("batchId") String batchId) {
        ModelAndView mav = new ModelAndView("batches/batchDetails");
        Batch batch = this.batchService.findById(batchId);

        mav.addObject(batch);
        return mav;
    }

//    @GetMapping("/batches/{batchId}/logs/new")
//    public String addNewLogToBatch(Batch batch, Map<String, Object> model){
//        Log log = new Log();
//        modelMap.put("log", log);
//
//        return "logs/new";
//    }

//    @PostMapping("/batches/{batchId}/logs/new")
//    public String addNewLogToBatch(Batch batch, @Valid Log log, BindingResult result, ModelMap modelMap){
//        batch.addLog(log);
//        modelMap.put("log", log);
//        if(result.hasErrors()){
//            modelMap.put("log", log);
//            return "logs/new";
//        }
//        else{
//            batchService.save(batch);
//            return "redirect:/batches/{batchId}";
//        }
//    }
}
