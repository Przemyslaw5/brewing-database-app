package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Log {

    private String id;
    private Batch batch;
    private LocalDate time;
    private double temp_in;
    private double temp_out;
    private double temp_set;
    private double epsilon;
}
