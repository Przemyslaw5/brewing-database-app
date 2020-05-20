package com.agh.database.brewingdatabaseapp.controllers;

import com.agh.database.brewingdatabaseapp.model.Ingredient;
import com.agh.database.brewingdatabaseapp.services.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService;
    }

    @GetMapping("{ingredientName}")
    public ModelAndView showBatch(@PathVariable("ingredientName") String ingredientName) {
        ModelAndView mav = new ModelAndView("ingredients/ingredientDetails");
        Ingredient ingredient = this.ingredientService.findByName(ingredientName).get(0);
        mav.addObject(ingredient);
        return mav;
    }
}
