package com.agh.database.brewingdatabaseapp.controllers;

import com.agh.database.brewingdatabaseapp.model.Ingredient;
import com.agh.database.brewingdatabaseapp.model.Inventory;
import com.agh.database.brewingdatabaseapp.services.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService;
    }

//    @ModelAttribute("ingredient")
//    public Ingredient findOwner(@PathVariable("ingredientName") String ingredientName) {
//        return this.ingredientService.findByName(ingredientName);
//    }

    @GetMapping("/{ingredientName}")
    public ModelAndView showBatch(@PathVariable("ingredientName") String ingredientName) {
        System.out.println("CZY DOCHODZI");
        ModelAndView mav = new ModelAndView("ingredients/ingredientDetails");
        System.out.println("CZY DOCHODZI" + ingredientName);
        Ingredient ingredient = this.ingredientService.findByName(ingredientName);
        System.out.println("CZY DOCHODZI");
        mav.addObject(ingredient);
        System.out.println("CZY DOCHODZI");
        return mav;
    }

    @GetMapping("{ingredientName}/new")
    public String initCreationForm(@PathVariable("ingredientName") String ingredientName, Model model) {
        Ingredient ingredient = ingredientService.findByName(ingredientName);
        //System.out.println("SIEMA" + ingredient.getName());
        Inventory inventory = new Inventory();
        inventory.setIngredientName(ingredientName);
        ingredient.addInventory(inventory);
        model.addAttribute("inventory", inventory);
        return "ingredients/newInventory";
    }

    @PostMapping("{ingredientName}/new")
    public String initCreationForm2(@Valid Inventory inventory, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("inventory", inventory);
            return "ingredients/newInventory";
        }
        else{
            Ingredient ingredient = ingredientService.findByName(inventory.getIngredientName());
            inventory.setId();
            ingredient.addInventory(inventory);
            ingredientService.save(ingredient);
            return "redirect:/ingredients/{ingredientName}";
        }
//
//        if (result.hasErrors()) {
//            Set<String> freezerNames = this.batchService.getUniqueFreezerNames();
//            model.addAttribute("freezers", freezerNames);
//            return "batches/new";
//        }
//        else {
//            batch.setFreezer(batchService.getFreezerByName(batch.getFreezer().getName()));
//            this.batchService.save(batch);
//            return "redirect:/batches/" + batch.getName();
//        }
    }
}
