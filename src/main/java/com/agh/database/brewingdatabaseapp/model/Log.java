package com.agh.database.brewingdatabaseapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Log {

    static private int highestID = 0;

    private int id;
    private LocalDate time;
    private double temp_in;
    private double temp_out;
    private double temp_set;
    private double epsilon;

    public Log(LocalDate time, double temp_in, double temp_out, double temp_set, double epsilon) {
        this.id = highestID++;
        this.time = time;
        this.temp_in = temp_in;
        this.temp_out = temp_out;
        this.temp_set = temp_set;
        this.epsilon = epsilon;
    }
}
