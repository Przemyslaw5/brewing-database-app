package com.agh.database.brewingdatabaseapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

//    @Autowired
//    private BatchRepository repository;
//
//
//    @PostMapping("/placeBatchNow")
//    public String placeBatch(@RequestBody Batch batch){
//        repository.save(batch);
//        return "Freezer placed successfully...";
//    }
//
//    @GetMapping("/getUserByName/{}")
//    public List<Batch> getFreezerByName(@PathVariable String name){
//        return repository.findByName(name);
//    }

    @RequestMapping({"/", ""})
    public String showSomething(Model model){

        //model.addAttribute("joke", jokeService.getJoke());

        return "index";
    }
}