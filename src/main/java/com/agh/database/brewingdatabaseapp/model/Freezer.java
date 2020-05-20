package com.agh.database.brewingdatabaseapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Freezer {

    private String name;
    private String address;
    private String city;
    private List<Batch> batches;

    public Freezer(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }
}