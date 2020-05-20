package com.agh.database.brewingdatabaseapp.services;

import com.agh.database.brewingdatabaseapp.model.Batch;
import com.agh.database.brewingdatabaseapp.repositories.BatchRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BatchService implements MongoService<Batch, String> {

    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    @Override
    public Set<Batch> findAll() {
        Set<Batch> batches = new HashSet<>();
        batchRepository.findAll().forEach(batches::add);
        return batches;
    }

    @Override
    public Batch findById(String s) {
        return batchRepository.findById(s).orElse(null);
    }

    public List<Batch> findByNameStartingWith(String s) {
        return batchRepository.findByNameStartingWith(s);
    }

    @Override
    public Batch save(Batch object) {
        return batchRepository.save(object);
    }

    @Override
    public void delete(Batch object) {
        batchRepository.delete(object);
    }

    @Override
    public void deleteById(String s) {
        batchRepository.deleteById(s);
    }

    public Set<String> getUniqueFreezerNames(){
        Set<Batch> batches = this.findAll();
        Set<String> freezerNames = new HashSet<>();
        for (Batch batch : batches) {
            freezerNames.add(batch.getFreezer().getName());
        }
        return freezerNames;
    }
}
