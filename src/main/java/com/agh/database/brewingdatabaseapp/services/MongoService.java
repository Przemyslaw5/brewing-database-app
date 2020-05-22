package com.agh.database.brewingdatabaseapp.services;

import java.util.Set;

public interface MongoService<T, ID> {

    Set<T> findAll();

    T findById(ID id);

    T save(T object);

    void delete(T object);

    void deleteById(ID id);

    void deleteAll();
}
