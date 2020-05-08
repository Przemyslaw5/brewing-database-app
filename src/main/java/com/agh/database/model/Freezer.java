package com.agh.database.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Freezer {

    @Id
    private String id;

    private String name;
}
