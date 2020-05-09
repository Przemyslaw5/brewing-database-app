package com.agh.database.brewingdatabaseapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Document(collation = "batch")
public class Batch {

    @Id
    private String id;
    private String name;
    @DBRef
    private Freezer freezer;
    private String style;
    private BatchType batchType;
    private LocalDate brewedDate;
    private LocalDate bottledDate;

    public Batch() {
    }

    public Batch(String id, String name, Freezer freezer, String style, BatchType batchType, LocalDate brewedDate, LocalDate bottledDate) {
        this.id = id;
        this.name = name;
        this.freezer = freezer;
        this.style = style;
        this.batchType = batchType;
        this.brewedDate = brewedDate;
        this.bottledDate = bottledDate;
    }
}