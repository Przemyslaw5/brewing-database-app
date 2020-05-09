package com.agh.database.brewingdatabaseapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Document(collation = "log")
public class Log {

    @Id
    private String id;
    private Batch batch;
    private LocalDate time;
    private double temp_in;
    private double temp_out;
    private double temp_set;
    private double epsilon;

    public Log() {
    }

    public Log(String id, Batch batch, LocalDate time, Double temp_in, Double temp_out, Double temp_set, Double epsilon) {
        this.id = id;
        this.batch = batch;
        this.time = time;
        this.temp_in = temp_in;
        this.temp_out = temp_out;
        this.temp_set = temp_set;
        this.epsilon = epsilon;
    }
}
