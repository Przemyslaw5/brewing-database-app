package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    private String ingredientName;
    private LocalDate timeBought;
    private int amount;
    private LocalDate bestBefore;
    private LocalDate opened;
}
