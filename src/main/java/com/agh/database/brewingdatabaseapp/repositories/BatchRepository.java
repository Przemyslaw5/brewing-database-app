package com.agh.database.brewingdatabaseapp.repositories;

import com.agh.database.brewingdatabaseapp.model.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BatchRepository extends MongoRepository<Batch, String> {
    List<Batch> findByName(String name);
}
