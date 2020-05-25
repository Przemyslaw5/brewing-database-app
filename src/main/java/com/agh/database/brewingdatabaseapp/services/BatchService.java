package com.agh.database.brewingdatabaseapp.services;

import com.agh.database.brewingdatabaseapp.model.Batch;
import com.agh.database.brewingdatabaseapp.model.Freezer;
import com.agh.database.brewingdatabaseapp.model.Mash;
import com.agh.database.brewingdatabaseapp.repositories.BatchRepository;
import com.agh.database.brewingdatabaseapp.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class BatchService implements MongoService<Batch, String> {

    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository, IngredientRepository ingredientRepository) {
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

    public Batch findByName(String s){
        return batchRepository.findByName(s);
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

    @Override
    public void deleteAll() {
        batchRepository.deleteAll();
    }

    public Set<Batch> getAllBatchesFromFreezer(Freezer freezer){
        Set<Batch> result = new HashSet<>();
        Set<Batch> batches = this.findAll();
        for(Batch batch : batches){
            if(freezer.equals(batch.getFreezer())){
                result.add(batch);
            }
        }
        return result;
    }

    public List<Mash> prepareMashesFromInputToBatch(List<Mash> mashes){
        int step = 1;
        List<Mash> mashesToBatch = new LinkedList<>();
        for(Mash mash : mashes){
            if(mash.getDurationMins() != 0 && mash.getTemp() != 0.0){
                mash.setStep(step++);
                mashesToBatch.add(mash);
            }
        }
        return mashesToBatch;
    }
}
