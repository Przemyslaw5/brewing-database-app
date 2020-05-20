package com.agh.database.brewingdatabaseapp.repositories;

import com.agh.database.brewingdatabaseapp.model.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {
}
