package com.agh.database.brewingdatabaseapp.bootstrap;

import com.agh.database.brewingdatabaseapp.model.*;
import com.agh.database.brewingdatabaseapp.services.BatchService;
import com.agh.database.brewingdatabaseapp.services.FreezerService;
import com.agh.database.brewingdatabaseapp.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("prod")
@Slf4j
public class ProdDataLoader implements CommandLineRunner {

    private final BatchService batchService;
    private final IngredientService ingredientService;
    private final FreezerService freezerService;

    public ProdDataLoader(BatchService batchService, IngredientService ingredientService, FreezerService freezerService) {
        this.batchService = batchService;
        this.ingredientService = ingredientService;
        this.freezerService = freezerService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Run bootstrap ProdDataLoader");

        batchService.deleteAll();
        ingredientService.deleteAll();
        freezerService.deleteAll();

        Freezer freezer = new Freezer("First freezer", "345 Maple St.","Madison");
        freezerService.save(freezer);

        Batch batch1 = new Batch();
        batch1.setBottledDate(LocalDate.now());
        batch1.setBrewedDate(LocalDate.now());
        batch1.setFreezer(freezer);
        batch1.setName("batch53");
        batch1.setStyle("style4124");
        batch1.setBatchType(BatchType.LAGER);


        Batch batch2 = new Batch();
        batch2.setBottledDate(LocalDate.now());
        batch2.setBrewedDate(LocalDate.now());
        batch2.setFreezer(freezer);
        batch2.setName("batch142");
        batch2.setStyle("wiedenski");
        batch2.setBatchType(BatchType.BELGIAN_ALE);
        batchService.save(batch2);
        batch1.addLog(new Log(LocalDate.now(), 10.0, 20.432, 54.42, 43.0));
        batch1.addLog(new Log(LocalDate.now(), 25.0, 32.5, 6.61, 20.31));
        batch1.addMash(new Mash(45, 300.23));
        batch1.addMash(new Mash(321, 125.64));

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Woda");
        ingredient1.setIngredientType(IngredientType.OTHER);
        ingredient1.setDescription("Spoko woda");
        Inventory inventory1 = new Inventory(ingredient1.getName(), LocalDate.now(), 26, LocalDate.now(), LocalDate.now());
        Inventory inventory2 = new Inventory(ingredient1.getName(), LocalDate.now(), 64, LocalDate.now(), LocalDate.now());
        ingredient1.addInventory(inventory1);
        ingredient1.addInventory(inventory2);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Drozdze polnocne");
        ingredient2.setIngredientType(IngredientType.YEAST);
        ingredient2.setDescription("Świezutkie");
        BatchIngredient batchIngredient1 = new BatchIngredient(batch1.getId(), ingredient1.getId(), ingredient1.getName(), 45, 573, TechniqueType.BOIL);
        ingredient1.addBatchIngredient(batchIngredient1);
        ingredient2.addBatchIngredient(batchIngredient1);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setName("Chmiel inslandzki");
        ingredient3.setIngredientType(IngredientType.HOPS);
        ingredient3.setDescription("Najlepszy w okolicy");
        Ingredient ingredient4 = new Ingredient();
        ingredient4.setName("Sol morska");
        ingredient4.setIngredientType(IngredientType.OTHER);
        ingredient4.setDescription("Kupiona w sklepie u Jadzi");
        BatchIngredient batchIngredient2 = new BatchIngredient(batch1.getId(), ingredient4.getId(), ingredient4.getName(), 124, 53, TechniqueType.DRY);
        ingredient3.addBatchIngredient(batchIngredient2);
        ingredient4.addBatchIngredient(batchIngredient2);

        batch1.addBatchIngredients(batchIngredient1);
        batch1.addBatchIngredients(batchIngredient2);
        batchService.save(batch1);

        ingredientService.save(ingredient1);
        ingredientService.save(ingredient2);
        ingredientService.save(ingredient3);
        ingredientService.save(ingredient4);
    }
}
