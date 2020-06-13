package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ingredient")
public class Ingredient {

    @Id
    private String id;
    @NotNull
    @Size(min=3, max=20, message = "Name must be between 3 and 20 characters.")
    private String name;
    @NotNull
    private IngredientType ingredientType;
    @NotNull
    private UnitIngredient unitIngredient;
    @NotNull
    private double unitsInStock;
    @NotNull
    @Size(min=7, max=100, message = "Description must be between 7 and 100 characters.")
    private String description;
    private List<BatchIngredient> batchIngredients;
    private List<Inventory> inventories;

    public Ingredient(String name, IngredientType ingredientType, UnitIngredient unitIngredient, double unitsInStock, String description) {
        this.name = name;
        this.ingredientType = ingredientType;
        this.unitIngredient = unitIngredient;
        this.unitsInStock = unitsInStock;
        this.description = description;
    }

    public void addBatchIngredient(BatchIngredient batchIngredient){
        if(this.batchIngredients == null){
            this.batchIngredients = new LinkedList<>();
        }
        this.batchIngredients.add(batchIngredient);
        this.unitsInStock -= batchIngredient.getAmount();
    }

    public void addInventory(Inventory inventory){
        if(this.inventories == null){
            this.inventories = new LinkedList<>();
        }
        this.unitsInStock += inventory.getAmount();
        this.inventories.add(inventory);
    }
}
