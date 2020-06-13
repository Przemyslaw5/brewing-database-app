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
import java.util.Set;

@Controller
@RequestMapping("ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService;
    }

    @GetMapping("")
    public String showAllIngredients(Model model){
        Set<Ingredient> ingredientSet = ingredientService.findAll();
        model.addAttribute("ingredients", ingredientSet);
        return "ingredients/list";
    }

    @GetMapping("/new")
    public String addNewIngredient(Model model){
        Ingredient ingredient = new Ingredient();
        model.addAttribute("ingredient", ingredient);
        return "ingredients/new";
    }

    @PostMapping("/new")
    public String saveNewIngredient(@Valid Ingredient ingredient, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("ingredient", ingredient);
            return "ingredients/new";
        }
        else{
            ingredientService.save(ingredient);
            return "redirect:/ingredients/" + ingredient.getName();
        }
    }

    @GetMapping("/{ingredientName}")
    public ModelAndView showBatch(@PathVariable("ingredientName") String ingredientName) {
        ModelAndView mav = new ModelAndView("ingredients/ingredientDetails");
        Ingredient ingredient = this.ingredientService.findByName(ingredientName);
        mav.addObject(ingredient);
        return mav;
    }

    @GetMapping("{ingredientName}/new")
    public String initCreationForm(@PathVariable("ingredientName") String ingredientName, Model model) {
        Ingredient ingredient = ingredientService.findByName(ingredientName);
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
            inventory.setTimeBought(inventory.getTimeBought().replace("T", " "));
            inventory.setOpened(inventory.getOpened().replace("T", " "));
            inventory.setBestBefore(inventory.getBestBefore().replace("T", " "));
            ingredient.addInventory(inventory);
            ingredientService.save(ingredient);
            return "redirect:/ingredients/{ingredientName}";
        }
    }

    @GetMapping("{ingredientName}/edit")
    public String initUpdateOwnerForm(@PathVariable("ingredientName") String ingredientName, Model model) {
        Ingredient ingredient = this.ingredientService.findByName(ingredientName);
        model.addAttribute("ingredient", ingredient);
        return "ingredients/edit";
    }

    @PostMapping("{ingredientName}/edit")
    public String processUpdateOwnerForm(@Valid Ingredient ingredient, BindingResult result, @PathVariable("ingredientName") String ingredientName) {
        if (result.hasErrors()) {
            return "ingredients/edit";
        }
        else {
            this.ingredientService.save(ingredient);
            return "redirect:/ingredients/{ingredientName}";
        }
    }
}
