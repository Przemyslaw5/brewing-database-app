package com.agh.database.brewingdatabaseapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collation = "mash")
public class Mash {

    @Id
    private String id;
    private Batch batch;
    private int step;
    private int durationMins;
    private double temp;

    public Mash() {
    }

    public Mash(String id, Batch batch, int step, int durationMins, double temp) {
        this.id = id;
        this.batch = batch;
        this.step = step;
        this.durationMins = durationMins;
        this.temp = temp;
    }
}
