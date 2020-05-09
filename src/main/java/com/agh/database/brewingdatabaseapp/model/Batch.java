package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}