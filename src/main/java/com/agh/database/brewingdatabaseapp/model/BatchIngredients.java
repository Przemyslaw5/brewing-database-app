package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BatchIngredients {

    private String id;
    private Ingredient ingredient;
    private int amount;
    private LocalDate time;
    private TechniqueType techniqueType;
    private Batch batch;
}
