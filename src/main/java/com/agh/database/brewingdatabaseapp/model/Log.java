package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
