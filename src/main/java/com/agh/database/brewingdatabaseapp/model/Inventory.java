package com.agh.database.brewingdatabaseapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Document(collation = "inventory")
public class Inventory {

    @Id
    private String id;
    private Ingredient ingredient;
    private LocalDate timeBought;
    private int amount;
    private LocalDate bestBefore;
    private LocalDate opened;

    public Inventory() {
    }

    public Inventory(String id, Ingredient ingredient, LocalDate timeBought, int amount, LocalDate bestBefore, LocalDate opened) {
        this.id = id;
        this.ingredient = ingredient;
        this.timeBought = timeBought;
        this.amount = amount;
        this.bestBefore = bestBefore;
        this.opened = opened;
    }
}
