package com.agh.database.brewingdatabaseapp.bootstrap;

import com.agh.database.brewingdatabaseapp.model.Batch;
import com.agh.database.brewingdatabaseapp.model.BatchType;
import com.agh.database.brewingdatabaseapp.model.Freezer;
import com.agh.database.brewingdatabaseapp.services.BatchService;
import com.agh.database.brewingdatabaseapp.services.FreezerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("dev")
@Slf4j
public class DevDataLoader implements CommandLineRunner {

    private final FreezerService freezerService;
    private final BatchService batchService;

    public DevDataLoader(FreezerService freezerService, BatchService batchService) {
        this.freezerService = freezerService;
        this.batchService = batchService;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("Run bootstrap DevDataLoader");
        Freezer freezer = new Freezer();
        freezer.setName("freezer1");
        freezerService.save(freezer);

        Batch batch = new Batch();
        batch.setBottledDate(LocalDate.now());
        batch.setBrewedDate(LocalDate.now());
        batch.setFreezer(freezer);
        batch.setName("batch1");
        batch.setStyle("style");
        batch.setBatchType(BatchType.LAGER);
        batchService.save(batch);
    }
}
