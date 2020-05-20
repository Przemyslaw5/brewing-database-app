package com.agh.database.brewingdatabaseapp.services;

import com.agh.database.brewingdatabaseapp.model.Ingredient;
import com.agh.database.brewingdatabaseapp.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class IngredientService implements MongoService<Ingredient, String> {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {

        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Set<Ingredient> findAll() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        return ingredients;
    }

    @Override
    public Ingredient findById(String s) {
        return ingredientRepository.findById(s).orElse(null);
    }

    public List<Ingredient> findByName(String s) {
        return ingredientRepository.findByName(s);
    }

    @Override
    public Ingredient save(Ingredient object) {
        return ingredientRepository.save(object);
    }

    @Override
    public void delete(Ingredient object) {
        ingredientRepository.delete(object);
    }

    @Override
    public void deleteById(String s) {
        ingredientRepository.deleteById(s);
    }
}
