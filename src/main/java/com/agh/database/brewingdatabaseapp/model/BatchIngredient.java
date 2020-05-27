package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchIngredient {

    private String batchID;
    private String ingredientName;
    private int amount;
    private int time;
    private TechniqueType techniqueType;

    public BatchIngredient(String ingredientName, int amount) {
        this.ingredientName = ingredientName;
        this.amount = amount;
    }
}
