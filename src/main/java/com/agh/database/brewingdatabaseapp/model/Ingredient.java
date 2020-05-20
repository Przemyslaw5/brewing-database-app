package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ingredient")
public class Ingredient {

    @Id
    private String id;
    private String name;
    private IngredientType ingredientType;
    private String description;
    private List<BatchIngredients> batchIngredients;
    private List<Inventory> inventories;
}
