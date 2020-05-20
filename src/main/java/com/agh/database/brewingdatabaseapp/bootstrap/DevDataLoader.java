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

        log.info("Run bootstrap DevDataLoader");
//        Freezer freezer = new Freezer("freezer1576", "345 Maple St.","Madison");

//        Batch batch1 = new Batch();
//        batch1.setBottledDate(LocalDate.now());
//        batch1.setBrewedDate(LocalDate.now());
//        batch1.setFreezer(freezer);
//        batch1.setName("batch53");
//        batch1.setStyle("style4124");
//        batch1.setBatchType(BatchType.LAGER);
//        batchService.save(batch1);

//        Batch batch2 = new Batch();
//        batch2.setBottledDate(LocalDate.now());
//        batch2.setBrewedDate(LocalDate.now());
//        batch2.setFreezer(freezer);
//        batch2.setName("batch142");
//        batch2.setStyle("wiedenski");
//        batch2.setBatchType(BatchType.BELGIAN_ALE);
//        batchService.save(batch2);
    }
}
