package com.agh.database.brewingdatabaseapp.bootstrap;

import com.agh.database.brewingdatabaseapp.services.BatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@Slf4j
public class DevDataLoader implements CommandLineRunner {

    private final BatchService batchService;

    public DevDataLoader(BatchService batchService) {
        this.batchService = batchService;
    }

    @Override
    public void run(String... args) throws Exception {

//        log.info("Run bootstrap DevDataLoader");
//        Freezer freezer = new Freezer("freezer1576", "345 Maple St.","Madison");
//
//        Batch batch = new Batch();
//        batch.setBottledDate(LocalDate.now());
//        batch.setBrewedDate(LocalDate.now());
//        batch.setFreezer(freezer);
//        batch.setName("batch53");
//        batch.setStyle("style4124");
//        batch.setBatchType(BatchType.LAGER);
//        batchService.save(batch);
    }
}
