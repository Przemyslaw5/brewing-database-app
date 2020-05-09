package com.agh.database.brewingdatabaseapp.bootstrap;

import com.agh.database.brewingdatabaseapp.model.Batch;
import com.agh.database.brewingdatabaseapp.model.BatchType;
import com.agh.database.brewingdatabaseapp.model.Freezer;
import com.agh.database.brewingdatabaseapp.repositories.BatchRepository;
import com.agh.database.brewingdatabaseapp.repositories.FreezerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("prod")
@Slf4j
@AllArgsConstructor
public class ProdDataLoader implements CommandLineRunner {

    private final FreezerRepository freezerRepository;
    private final BatchRepository batchRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Run bootstrap ProdDataLoader");
        Freezer freezer = new Freezer();
        freezer.setName("freezer154");
        freezerRepository.save(freezer);

        Batch batch = new Batch();
        batch.setBottledDate(LocalDate.now());
        batch.setBrewedDate(LocalDate.now());
        batch.setFreezer(freezer);
        batch.setName("batch53");
        batch.setStyle("style4124");
        batch.setBatchType(BatchType.LAGER);
        batchRepository.save(batch);
    }
}
