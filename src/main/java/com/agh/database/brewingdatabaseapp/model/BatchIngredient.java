package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BatchIngredient {

    private String batchID;
    private String ingredientID;
    private String ingredientName;
    private int amount;
    private LocalDate time;
    private TechniqueType techniqueType;
}
