package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    static private int highestID = 0;

    private int id;
    private String ingredientName;
    @NotNull(message = "You mast set bought date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate timeBought;
    @NotNull
    @Min(value = 1, message = "Amount must be higher")
    private int amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bestBefore;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate opened;

    public void setId() {
        this.id = highestID++;
    }

    public Inventory(String ingredientName, LocalDate timeBought, int amount, LocalDate bestBefore, LocalDate opened) {
        this.id = highestID++;
        this.ingredientName = ingredientName;
        this.timeBought = timeBought;
        this.amount = amount;
        this.bestBefore = bestBefore;
        this.opened = opened;
    }
}
