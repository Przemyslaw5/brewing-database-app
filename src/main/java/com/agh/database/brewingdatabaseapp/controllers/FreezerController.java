package com.agh.database.brewingdatabaseapp.controllers;

import com.agh.database.brewingdatabaseapp.model.Batch;
import com.agh.database.brewingdatabaseapp.model.Freezer;
import com.agh.database.brewingdatabaseapp.services.BatchService;
import com.agh.database.brewingdatabaseapp.services.FreezerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class FreezerController {

    private final FreezerService freezerService;
    private final BatchService batchService;

    public FreezerController(FreezerService freezerService, BatchService batchService) {
        this.freezerService = freezerService;
        this.batchService = batchService;
    }


    @GetMapping("freezers")
    public String showListAllFreezers(Model model){
        Set<Freezer> freezerNames = freezerService.findAll();
        model.addAttribute("freezers", freezerNames);
        return "freezers/list";
    }

    @GetMapping("/freezers/{freezerName}")
    public String showFreezer(@PathVariable("freezerName") String freezerName, Model model) {
        Freezer freezer = this.freezerService.findByName(freezerName);
        Set<Batch> batches = batchService.getAllBatchesFromFreezer(freezer);
        model.addAttribute("freezer", freezer);
        model.addAttribute("batches", batches);
        return "freezers/freezerDetails";
    }

    @GetMapping("/freezers/new")
    public String addNewFreezer(Model model) {
        Freezer freezer = new Freezer();
        model.addAttribute("freezer", freezer);
        return "freezers/new";
    }

    @PostMapping("/freezers/new")
    public String saveNewFreezer(@Valid Freezer freezer, BindingResult result, Model model) {
        Freezer f = freezerService.findByName(freezer.getName());
        if (result.hasErrors() || f != null) {
            if (f != null) {
                FieldError nameError = new FieldError("name", "name", "Freezer with that name already exists.");
                result.addError(nameError);
            }
            model.addAttribute("freezer", freezer);
            return "freezers/new";
        }
        else{
            freezerService.save(freezer);
            return "redirect:/freezers/" + freezer.getName();
        }
    }
}
