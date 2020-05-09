package com.agh.database.brewingdatabaseapp.repositories;

import com.agh.database.brewingdatabaseapp.model.Freezer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FreezerRepository extends MongoRepository<Freezer, String> {
}