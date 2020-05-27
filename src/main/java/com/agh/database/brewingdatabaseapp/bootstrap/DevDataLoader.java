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
@Profile("dev")
@Slf4j
public class DevDataLoader implements CommandLineRunner {

    private final BatchService batchService;
    private final IngredientService ingredientService;
    private final FreezerService freezerService;

    public DevDataLoader(BatchService batchService, IngredientService ingredientService, FreezerService freezerService) {
        this.batchService = batchService;
        this.ingredientService = ingredientService;
        this.freezerService = freezerService;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("Run bootstrap DevDataLoader");

        batchService.deleteAll();
        ingredientService.deleteAll();
        freezerService.deleteAll();

        Freezer freezer1 = new Freezer("Fast freezer", "884  Kerry Way","Pomona");
        Freezer freezer2 = new Freezer("freezer975", "1977  Duffy Street","Portage");
        Freezer freezer3 = new Freezer("F-25", "2237  Farnum Road","New York");
        Freezer freezer4 = new Freezer("official freezer", "4502  Wood Duck Drive","Watton");
        Freezer freezer5 = new Freezer("the best", "1337  Lochmere Lane","New London");
        freezerService.save(freezer1);
        freezerService.save(freezer2);
        freezerService.save(freezer3);
        freezerService.save(freezer4);
        freezerService.save(freezer5);

        Batch batch1 = new Batch("first batch", freezer1, "Dunkel", BatchType.LAGER, LocalDate.of(2019, 10, 23), LocalDate.of(2019, 11, 10));
        Batch batch2 = new Batch("Best batch", freezer2, "Flanders red ale", BatchType.ALE, LocalDate.of(2020, 3, 15), LocalDate.of(2020, 3, 18));
        Batch batch3 = new Batch("batch53", freezer3, "Vienna lager", BatchType.LAGER, LocalDate.of(2020, 1, 11), LocalDate.of(2020, 1, 12));
        Batch batch4 = new Batch("beer for testing", freezer3, "India pale ale", BatchType.BELGIAN_ALE, LocalDate.of(2020, 5, 9), LocalDate.of(2020, 5, 16));
        batchService.save(batch1);
        batchService.save(batch2);
        batchService.save(batch3);
        batchService.save(batch4);

        batch1.addLog(new Log(LocalDate.of(2019, 11, 10), 10.3, 2.14, 2.0, 9.5));
        batch1.addLog(new Log(LocalDate.of(2019, 11, 11), 9.52, 2.02, 2.0, 9.5));
        batch1.addLog(new Log(LocalDate.of(2019, 11, 12), 10.11, 2.21, 2.0, 9.5));
        batch1.addMash(new Mash(1, 45, 300.23));
        batch1.addMash(new Mash(2, 321, 125.64));

        batch2.addLog(new Log(LocalDate.of(2020, 3, 18), 9.23, 5.25, 5.0, 4.0));
        batch2.addLog(new Log(LocalDate.of(2020, 3, 23), 9.64, 5.63, 5.0, 4.0));
        batch2.addMash(new Mash(1, 63, 12.32));

        batch3.addLog(new Log(LocalDate.of(2020, 1, 12), 14.53, 9.42, 9.0, 6.0));
        batch3.addLog(new Log(LocalDate.of(2020, 1, 13), 14.63, 9.12, 9.0, 6.0));
        batch3.addLog(new Log(LocalDate.of(2020, 1, 14), 14.21, 10.05, 9.5, 6.0));
        batch3.addLog(new Log(LocalDate.of(2020, 1, 15), 14.87, 8.99, 9.0, 6.0));
        batch3.addLog(new Log(LocalDate.of(2020, 1, 16), 14.74, 9.24, 9.0, 6.0));
        batch3.addMash(new Mash(1, 532, 123.32));
        batch3.addMash(new Mash(2, 23, 53.23));
        batch3.addMash(new Mash(3, 643, 74.27));
        batch3.addMash(new Mash(4, 313, 34.63));

        batch4.addLog(new Log(LocalDate.of(2020, 5, 16), 22.31, 15.23, 15.0, 10.5));
        batch4.addLog(new Log(LocalDate.of(2020, 5, 18), 22.63, 16.75, 16.0, 10.5));
        batch4.addLog(new Log(LocalDate.of(2020, 5, 25), 21.85, 15.77, 15.0, 10.5));
        batch4.addLog(new Log(LocalDate.of(2020, 6, 1), 23.02, 15.24, 15.0, 10.5));
        batch4.addMash(new Mash(1, 421, 123.31));
        batch4.addMash(new Mash(2, 21, 23.42));
        batch4.addMash(new Mash(3, 873, 353.43));

        Ingredient ingredient1 = new Ingredient("Water", IngredientType.OTHER, "Nice water");
        Inventory inventory1 = new Inventory(ingredient1.getName(), LocalDate.of(2019, 12, 28), 19812, LocalDate.of(2020, 8, 5), LocalDate.of(2020, 12, 29));
        Inventory inventory2 = new Inventory(ingredient1.getName(), LocalDate.of(2020, 3, 19), 5311, LocalDate.of(2021, 3, 14), null);
        Inventory inventory3 = new Inventory(ingredient1.getName(), LocalDate.of(2020, 5, 3), 12434, LocalDate.of(2021, 6, 1), null);
        ingredient1.addInventory(inventory1);
        ingredient1.addInventory(inventory2);
        ingredient1.addInventory(inventory3);
        ingredientService.save(ingredient1);

        Ingredient ingredient2 = new Ingredient("Yeast north", IngredientType.YEAST, "Freshly");
        Inventory inventory4 = new Inventory(ingredient2.getName(), LocalDate.of(2020, 3, 12), 153, LocalDate.of(2020, 6, 19), LocalDate.of(2020, 12, 29));
        Inventory inventory5 = new Inventory(ingredient2.getName(), LocalDate.of(2020, 4, 14), 613, LocalDate.of(2021, 7, 1), null);
        ingredient2.addInventory(inventory4);
        ingredient2.addInventory(inventory5);
        ingredientService.save(ingredient2);

        Ingredient ingredient3 = new Ingredient("Icelandic hops", IngredientType.HOPS, "The best around");
        Inventory inventory6 = new Inventory(ingredient3.getName(), LocalDate.of(2020, 4, 9), 6984, LocalDate.of(2020, 9, 23), LocalDate.of(2020, 5, 22));
        ingredient3.addInventory(inventory6);
        ingredientService.save(ingredient3);

        Ingredient ingredient4 = new Ingredient("Sea salt", IngredientType.OTHER, "Bought in a local shop");
        Inventory inventory7 = new Inventory(ingredient4.getName(), LocalDate.of(2020, 1, 23), 4314, LocalDate.of(2020, 11, 20), LocalDate.of(2020, 4, 9));
        Inventory inventory8 = new Inventory(ingredient4.getName(), LocalDate.of(2020, 4, 1), 6424, LocalDate.of(2021, 2, 3), null);
        ingredient4.addInventory(inventory7);
        ingredient4.addInventory(inventory8);
        ingredientService.save(ingredient4);

        //WATER
        BatchIngredient batchIngredient1 = new BatchIngredient(batch1.getId(), ingredient1.getName(), 2313, 532, TechniqueType.BOIL);
        BatchIngredient batchIngredient2 = new BatchIngredient(batch2.getId(), ingredient1.getName(), 623, 99, TechniqueType.BOIL);
        BatchIngredient batchIngredient3 = new BatchIngredient(batch3.getId(), ingredient1.getName(), 764, 54, TechniqueType.BOIL);
        BatchIngredient batchIngredient4 = new BatchIngredient(batch4.getId(), ingredient1.getName(), 12414, 754, TechniqueType.BOIL);
        ingredient1.addBatchIngredient(batchIngredient1);
        ingredient1.addBatchIngredient(batchIngredient2);
        ingredient1.addBatchIngredient(batchIngredient3);
        ingredient1.addBatchIngredient(batchIngredient4);
        batch1.addBatchIngredients(batchIngredient1);
        batch2.addBatchIngredients(batchIngredient2);
        batch3.addBatchIngredients(batchIngredient3);
        batch4.addBatchIngredients(batchIngredient4);

        //YEAST NORTH
        BatchIngredient batchIngredient5 = new BatchIngredient(batch2.getId(), ingredient2.getName(), 51, 12, TechniqueType.DRY);
        BatchIngredient batchIngredient6 = new BatchIngredient(batch3.getId(), ingredient2.getName(), 64, 34, TechniqueType.DRY);
        ingredient2.addBatchIngredient(batchIngredient5);
        ingredient2.addBatchIngredient(batchIngredient6);
        batch2.addBatchIngredients(batchIngredient5);
        batch3.addBatchIngredients(batchIngredient6);

        //ICELANDIC
        BatchIngredient batchIngredient7 = new BatchIngredient(batch1.getId(), ingredient3.getName(), 65, 64, TechniqueType.DRY);
        BatchIngredient batchIngredient8 = new BatchIngredient(batch3.getId(), ingredient3.getName(), 21, 86, TechniqueType.DRY);
        BatchIngredient batchIngredient9 = new BatchIngredient(batch4.getId(), ingredient3.getName(), 12, 25, TechniqueType.DRY);
        ingredient3.addBatchIngredient(batchIngredient7);
        ingredient3.addBatchIngredient(batchIngredient8);
        ingredient3.addBatchIngredient(batchIngredient9);
        batch1.addBatchIngredients(batchIngredient7);
        batch3.addBatchIngredients(batchIngredient8);
        batch4.addBatchIngredients(batchIngredient9);

        //SEA SALT
        BatchIngredient batchIngredient10 = new BatchIngredient(batch1.getId(), ingredient4.getName(), 12, 86, TechniqueType.BOIL);
        BatchIngredient batchIngredient11 = new BatchIngredient(batch3.getId(), ingredient4.getName(), 53, 33, TechniqueType.BOIL);
        BatchIngredient batchIngredient12 = new BatchIngredient(batch3.getId(), ingredient4.getName(), 21, 75, TechniqueType.BOIL);
        BatchIngredient batchIngredient13 = new BatchIngredient(batch4.getId(), ingredient4.getName(), 32, 12, TechniqueType.BOIL);
        ingredient4.addBatchIngredient(batchIngredient10);
        ingredient4.addBatchIngredient(batchIngredient11);
        ingredient4.addBatchIngredient(batchIngredient12);
        ingredient4.addBatchIngredient(batchIngredient13);
        batch1.addBatchIngredients(batchIngredient10);
        batch2.addBatchIngredients(batchIngredient11);
        batch3.addBatchIngredients(batchIngredient12);
        batch4.addBatchIngredients(batchIngredient13);

        batchService.save(batch1);
        batchService.save(batch2);
        batchService.save(batch3);
        batchService.save(batch4);

        ingredientService.save(ingredient1);
        ingredientService.save(ingredient2);
        ingredientService.save(ingredient3);
        ingredientService.save(ingredient4);
    }
}
