package com.agh.database.brewingdatabaseapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collation = "ingredient")
public class Ingredient {

    @Id
    private String id;
    private String name;
    private IngredientType ingredientType;
    private String description;

    public Ingredient() {
    }

    public Ingredient(String id, String name, IngredientType ingredientType, String description) {
        this.id = id;
        this.name = name;
        this.ingredientType = ingredientType;
        this.description = description;
    }
}
