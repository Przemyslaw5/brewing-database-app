package com.agh.database.brewingdatabaseapp.bootstrap;

import com.agh.database.brewingdatabaseapp.services.BatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@Slf4j
public class ProdDataLoader implements CommandLineRunner {

    private final BatchService batchService;

    public ProdDataLoader(BatchService batchService) {
        this.batchService = batchService;
    }

    @Override
    public void run(String... args) throws Exception {
//        log.info("Run bootstrap ProdDataLoader");
//        Freezer freezer = new Freezer("freezer154", "345 Maple St.","Madison");
//
//        Batch batch = new Batch();
//        batch.setBottledDate(LocalDate.now());
//        batch.setBrewedDate(LocalDate.now());
//        batch.setFreezer(freezer);
//        batch.setName("batch5476");
//        batch.setStyle("style412489");
//        batch.setBatchType(BatchType.LAGER);
//        batchService.save(batch);
    }
}
