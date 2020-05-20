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
@Document(collation = "inventory")
public class Inventory {

    @Id
    private String id;
    private LocalDate timeBought;
    private int amount;
    private LocalDate bestBefore;
    private LocalDate opened;
}
