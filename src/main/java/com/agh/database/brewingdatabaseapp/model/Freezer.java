package com.agh.database.brewingdatabaseapp.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Freezer {

    private String name;
    private String address;
    private String city;

    public Freezer(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }
}