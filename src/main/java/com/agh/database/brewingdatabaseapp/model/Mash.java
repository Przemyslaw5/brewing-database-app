package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Mash {

    private String id;
    private Batch batch;
    private int step;
    private int durationMins;
    private double temp;
}
