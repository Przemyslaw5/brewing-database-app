package com.agh.database.brewingdatabaseapp.services;

import com.agh.database.brewingdatabaseapp.model.Freezer;
import com.agh.database.brewingdatabaseapp.repositories.FreezerRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FreezerService implements MongoService<Freezer, String> {

    private final FreezerRepository freezerRepository;

    public FreezerService(FreezerRepository freezerRepository) {
        this.freezerRepository = freezerRepository;
    }

    @Override
    public Set<Freezer> findAll() {
        Set<Freezer> freezers = new HashSet<>();
        freezerRepository.findAll().forEach(freezers::add);
        return freezers;
    }

    @Override
    public Freezer findById(String s) {
        return freezerRepository.findById(s).orElse(null);
    }

    @Override
    public Freezer save(Freezer object) {
        return freezerRepository.save(object);
    }

    @Override
    public void delete(Freezer object) {
        freezerRepository.delete(object);
    }

    @Override
    public void deleteById(String s) {
        freezerRepository.deleteById(s);
    }
}
