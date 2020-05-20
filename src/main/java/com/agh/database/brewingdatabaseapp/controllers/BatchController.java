package com.agh.database.brewingdatabaseapp.controllers;

import com.agh.database.brewingdatabaseapp.model.Batch;
import com.agh.database.brewingdatabaseapp.services.BatchService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

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
}
