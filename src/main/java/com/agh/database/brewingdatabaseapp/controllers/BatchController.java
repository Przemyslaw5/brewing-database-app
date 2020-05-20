package com.agh.database.brewingdatabaseapp.controllers;

import com.agh.database.brewingdatabaseapp.model.Batch;
import com.agh.database.brewingdatabaseapp.model.Freezer;
import com.agh.database.brewingdatabaseapp.services.BatchService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
            matchingBatches = this.batchService.findByNameStartingWith("");
        }
        else{
            matchingBatches = this.batchService.findByNameStartingWith(batch.getName());
        }

        if (matchingBatches.isEmpty()) {
            result.rejectValue("name", "notFound", "There is no batch of beer with this name.");
            return "batches/findBatches";
        }
        else {
            model.put("matchingbatches", matchingBatches);
            return "batches/batchesList";
        }
    }

    @GetMapping("/batches/new")
    public String initCreationForm(Map<String, Object> model) {
        Batch batch = new Batch();
        model.put("batch", batch);

        Set<Freezer> freezerSet = this.batchService.getUniqueFreezers();
        model.put("freezers", freezerSet);

        return "batches/new";
    }

    @PostMapping("/batches/new")
    public String processCreationForm(@Valid Batch batch, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Niepoprawne dane");
            return "batches/new";
        }
        else {
            System.out.println("Poprawne dane");
            this.batchService.save(batch);
            return "batches/new";
        }
    }
}
