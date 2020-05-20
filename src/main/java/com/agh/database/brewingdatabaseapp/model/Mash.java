package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mash {

    private int step;
    private int durationMins;
    private double temp;

    public Mash(int durationMins, double temp) {
        this.durationMins = durationMins;
        this.temp = temp;
    }
}
