package com.agh.database.brewingdatabaseapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Document(collation = "batch_ingredients")
public class BatchIngredients {

    @Id
    private String id;
    private Ingredient ingredient;
    private int amount;
    private LocalDate time;
    private Technique technique;

    public BatchIngredients() {
    }

    public BatchIngredients(String id, Ingredient ingredient, int amount, LocalDate time, Technique technique) {
        this.id = id;
        this.ingredient = ingredient;
        this.amount = amount;
        this.time = time;
        this.technique = technique;
    }
}
