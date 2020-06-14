package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    static private int highestID = 0;

    private int id;
    private String ingredientName;
    @NotNull(message = "You mast set bought date")
    private String timeBought;
    @NotNull
    @Min(value = 1, message = "Amount must be higher")
    private double amount;
    private double amountAvailable;
    private String bestBefore;
    private String opened;

    public void setId() {
        this.id = highestID++;
    }

    public void setAmountAvailable(double amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public Inventory(String ingredientName, LocalDateTime timeBought, int amount, LocalDateTime bestBefore, LocalDateTime opened) {
        this.id = highestID++;
        this.ingredientName = ingredientName;
        this.timeBought = timeBought.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.amountAvailable = this.amount = amount;
        if(bestBefore == null) this.bestBefore = "";
        else this.bestBefore = bestBefore.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        if(opened == null) this.opened = "";
        else this.opened = opened.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
