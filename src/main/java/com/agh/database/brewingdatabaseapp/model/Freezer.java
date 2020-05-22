package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "freezer")
public class Freezer {

    @Id
    private String id;
    @NotNull
    @Size(min=5, max=20, message = "Name must be between 5 and 20 characters.")
    private String name;
    @NotNull
    @Size(min=1, message = "Address must be set")
    private String address;
    @NotNull
    @Size(min=1, message = "City must be set")
    private String city;

    public Freezer(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Freezer freezer = (Freezer) o;
        return Objects.equals(name, freezer.name) &&
                Objects.equals(address, freezer.address) &&
                Objects.equals(city, freezer.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, city);
    }
}