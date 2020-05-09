package com.agh.database.brewingdatabaseapp.bootstrap;

import com.agh.database.brewingdatabaseapp.model.Batch;
import com.agh.database.brewingdatabaseapp.model.BatchType;
import com.agh.database.brewingdatabaseapp.model.Freezer;
import com.agh.database.brewingdatabaseapp.repositories.BatchRepository;
import com.agh.database.brewingdatabaseapp.repositories.FreezerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
//@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final FreezerRepository freezerRepository;
    private final BatchRepository batchRepository;

    public DataLoader(FreezerRepository freezerRepository, BatchRepository batchRepository){
        this.freezerRepository = freezerRepository;
        this.batchRepository = batchRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Freezer freezer = new Freezer();
        freezer.setName("freezer1");
        freezerRepository.save(freezer);

        Batch batch = new Batch();
        batch.setBottledDate(LocalDate.now());
        batch.setBrewedDate(LocalDate.now());
        batch.setFreezer(freezer);
        batch.setName("batch1");
        batch.setStyle("style");
        batch.setBatchType(BatchType.LAGER);
        batchRepository.save(batch);
    }
}