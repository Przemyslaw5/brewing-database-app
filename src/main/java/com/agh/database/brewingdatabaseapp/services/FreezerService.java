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

    public Freezer findByName(String s){
        return freezerRepository.findByName(s);
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

    @Override
    public void deleteAll() {
        freezerRepository.deleteAll();
    }

    public Set<String> getUniqueFreezerNames(){
        Set<Freezer> freezers = this.findAll();
        Set<String> freezerNames = new HashSet<>();
        for (Freezer freezer : freezers) {
            freezerNames.add(freezer.getName());
        }
        return freezerNames;
    }
}
