package com.agh.database.brewingdatabaseapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document(collation = "freezer")
public class Freezer {

    @Id
    private String id;
    private String name;

    public Freezer() {
    }

    public Freezer(String id, String name) {
        this.id = id;
        this.name = name;
    }
}