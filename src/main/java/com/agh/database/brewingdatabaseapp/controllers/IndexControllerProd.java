package com.agh.database.brewingdatabaseapp.controllers;

import com.agh.database.brewingdatabaseapp.services.BatchService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Profile("prod")
public class IndexControllerProd {

    private final BatchService batchService;

    public IndexControllerProd(BatchService batchService) {
        this.batchService = batchService;
    }

    @RequestMapping({"/", ""})
    public String showOneBatch(Model model){
        model.addAttribute("batch", batchService.findById("5eb6598ac4afbc5dccd17ee4"));
        return "index";
    }
}