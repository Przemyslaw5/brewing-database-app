package com.agh.database.brewingdatabaseapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class Log {

    static private int highestID = 0;

    private int id;
    @NotNull
    private String time;
    @NotNull
    private double temp_in;
    @NotNull
    private double temp_out;
    @NotNull
    private double temp_set;
    @NotNull
    private double epsilon;

    public Log(LocalDateTime time, double temp_in, double temp_out, double temp_set, double epsilon) {
        this.id = highestID++;
        this.time = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.temp_in = temp_in;
        this.temp_out = temp_out;
        this.temp_set = temp_set;
        this.epsilon = epsilon;
    }

    public void setId(){
        this.id = highestID++;
    }
}
