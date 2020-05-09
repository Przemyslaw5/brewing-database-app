package com.agh.database.brewingdatabaseapp.controllers;

import com.agh.database.brewingdatabaseapp.services.BatchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final BatchService batchService;

    public IndexController(BatchService batchService) {
        this.batchService = batchService;
    }

    @RequestMapping({"/", ""})
    public String showOneBatch(Model model){
        model.addAttribute("batch", batchService.findById("5eb6a8d9c2f1ab25a71ff3fc"));
        return "index";
    }
}