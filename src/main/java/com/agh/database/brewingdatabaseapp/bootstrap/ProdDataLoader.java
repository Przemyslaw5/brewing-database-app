package com.agh.database.brewingdatabaseapp.bootstrap;

import com.agh.database.brewingdatabaseapp.model.*;
import com.agh.database.brewingdatabaseapp.services.BatchService;
import com.agh.database.brewingdatabaseapp.services.FreezerService;
import com.agh.database.brewingdatabaseapp.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

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

    public void addNLogs(Batch batch, int n, LocalDateTime localDateTime, double minTempIn, double maxTempIn, double minTempOut, double maxTempOut, double minTempSet, double maxTempSet, double minEpsilon, double maxEpsilon){
        Random r = new Random();

        for(int i = 0; i < n; i++){
            double randomTempIn = Math.round((minTempIn + (maxTempIn - minTempIn) * r.nextDouble()) * 100.0) / 100.0;
            double randomTempOut = Math.round((minTempOut + (maxTempOut - minTempOut) * r.nextDouble()) * 100.0) / 100.0;
            double randomTempSet = Math.round((minTempSet + (maxTempSet - minTempSet) * r.nextDouble()) * 100.0) / 100.0;
            double randomEpsilon = Math.round((minEpsilon + (maxEpsilon - minEpsilon) * r.nextDouble()) * 100.0) / 100.0;
            batch.addLog(new Log(localDateTime, randomTempIn, randomTempOut, randomTempSet, randomEpsilon));
            localDateTime = localDateTime.plusMinutes(40 + Math.abs(r.nextInt() % 41)); //add random number minutes from range 40 to 80
        }
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Run bootstrap ProdDataLoader");

        batchService.deleteAll();
        ingredientService.deleteAll();
        freezerService.deleteAll();

        Freezer freezer1 = new Freezer("freezer no. 1", "884  Kerry Way", "Krakow");
        Freezer freezer2 = new Freezer("old soviet freezer", "1977  Duffy Street", "Czestochowa");
        freezerService.save(freezer1);
        freezerService.save(freezer2);

        Batch batch1 = new Batch("01-WC", freezer1, "West Coast Ipa", BatchType.WC_IPA, LocalDateTime.of(2019, 10, 23, 13, 52), LocalDateTime.of(2019, 11, 10, 5, 32));
        Batch batch2 = new Batch("02-Saison", freezer2, "Saison", BatchType.SAISON, LocalDateTime.of(2020, 3, 15, 8, 12), LocalDateTime.of(2020, 3, 18, 9, 43));
        Batch batch3 = new Batch("03-APA", freezer1, "American Rale Ale", BatchType.APA, LocalDateTime.of(2020, 1, 11, 22, 45), LocalDateTime.of(2020, 1, 12, 19, 38));
        Batch batch4 = new Batch("04-IRA", freezer2, "Irish Red Ale", BatchType.IRA, LocalDateTime.of(2020, 5, 9, 17, 37), LocalDateTime.of(2020, 5, 16, 20, 28));
        batchService.save(batch1);
        batchService.save(batch2);
        batchService.save(batch3);
        batchService.save(batch4);


        addNLogs(batch1, 95, LocalDateTime.of(2019, 11, 10, 10, 42), 11, 13, 11, 13, 12, 12, 1, 1);
        batch1.addMash(new Mash(1, 120, 60));
        batch1.addMash(new Mash(2, 50, 65));
        batch1.addMash(new Mash(3, 30, 76));

        addNLogs(batch2, 112, LocalDateTime.of(2020, 3, 18, 14, 43), 9, 11, 9, 11, 10, 10, 1, 1);
        batch2.addMash(new Mash(1, 150, 64));
        batch2.addMash(new Mash(2, 30, 77));

        addNLogs(batch3, 74, LocalDateTime.of(2020, 1, 12, 13, 24), 14, 15, 8.5, 10.5, 9, 9.5, 5.9, 6.1);
        batch3.addMash(new Mash(1, 120, 65));
        batch3.addMash(new Mash(1, 30, 76));

        addNLogs(batch4, 102, LocalDateTime.of(2020, 5, 16, 20, 39), 20, 25, 14, 16, 15, 16, 10, 10.5);
        batch4.addMash(new Mash(1, 150, 64));
        batch4.addMash(new Mash(2, 20, 50));
        batch4.addMash(new Mash(3, 30, 76));

        Ingredient ingredient1 = new Ingredient("Water", IngredientType.OTHER, UnitIngredient.LITRE, 0, "Nice water");
        Inventory inventory1 = new Inventory(ingredient1.getName(), LocalDateTime.of(2019, 12, 28, 15, 53), 20, LocalDateTime.of(2020, 8, 5, 12, 42), LocalDateTime.of(2020, 12, 29, 0, 0));
        Inventory inventory2 = new Inventory(ingredient1.getName(), LocalDateTime.of(2020, 3, 19, 14, 3), 30, LocalDateTime.of(2021, 3, 14, 0, 0), null);
        Inventory inventory3 = new Inventory(ingredient1.getName(), LocalDateTime.of(2020, 5, 3, 12, 53), 50, LocalDateTime.of(2021, 6, 1, 0, 0), null);
        ingredient1.addInventory(inventory1);
        ingredient1.addInventory(inventory2);
        ingredient1.addInventory(inventory3);
        ingredientService.save(ingredient1);

        Ingredient ingredient2 = new Ingredient("US-05", IngredientType.YEAST, UnitIngredient.PACKAGE, 0, "most popular yeast");
        Inventory inventory4 = new Inventory(ingredient2.getName(), LocalDateTime.of(2019, 11, 10, 0, 0), 1, LocalDateTime.of(2019, 11, 10, 0, 0), LocalDateTime.of(2019, 11, 10, 0, 0));
        Inventory inventory5 = new Inventory(ingredient2.getName(), LocalDateTime.of(2020, 3, 15, 0, 0), 1, LocalDateTime.of(2020, 3, 15, 0, 0), LocalDateTime.of(2020, 3, 15, 0, 0));
        Inventory inventory6 = new Inventory(ingredient2.getName(), LocalDateTime.of(2020, 1, 11, 0, 0), 1, LocalDateTime.of(2020, 1, 11, 0, 0), LocalDateTime.of(2020, 1, 11, 0, 0));
        Inventory inventory7 = new Inventory(ingredient2.getName(), LocalDateTime.of(2020, 5, 9, 0, 0), 1, LocalDateTime.of(2020, 5, 9, 0, 0), LocalDateTime.of(2020, 5, 9, 0, 0));
        ingredient2.addInventory(inventory4);
        ingredient2.addInventory(inventory5);
        ingredient2.addInventory(inventory6);
        ingredient2.addInventory(inventory7);
        ingredientService.save(ingredient2);

        Ingredient ingredient3 = new Ingredient("Mosaic", IngredientType.HOPS, UnitIngredient.GRAMS, 0, "alpha acid ~11.5-13.5%");
        Inventory inventory8 = new Inventory(ingredient3.getName(), LocalDateTime.of(2019, 11, 8, 0, 0), 300, LocalDateTime.of(2020, 9, 23, 0, 0), LocalDateTime.of(2019, 11, 10, 0, 0));
        ingredient3.addInventory(inventory8);
        ingredientService.save(ingredient3);

        Ingredient ingredient4 = new Ingredient("Citra", IngredientType.HOPS, UnitIngredient.GRAMS, 0, "alpha acid ~10-15%");
        Inventory inventory9 = new Inventory(ingredient3.getName(), LocalDateTime.of(2019, 11, 8, 0, 0), 400, LocalDateTime.of(2020, 9, 23, 0, 0), LocalDateTime.of(2019, 11, 10, 0, 0));
        ingredient4.addInventory(inventory9);
        ingredientService.save(ingredient4);

        Ingredient ingredient5 = new Ingredient("Chinook", IngredientType.HOPS, UnitIngredient.GRAMS, 0, "alpha acid ~12-14%");
        Inventory inventory10 = new Inventory(ingredient3.getName(), LocalDateTime.of(2019, 11, 8, 0, 0), 400, LocalDateTime.of(2020, 9, 23, 0, 0), LocalDateTime.of(2019, 11, 10, 0, 0));
        ingredient5.addInventory(inventory10);
        ingredientService.save(ingredient5);

        Ingredient ingredient6 = new Ingredient("Barley", IngredientType.MALT, UnitIngredient.GRAMS, 0, "just some barley malt");
        Inventory inventory11 = new Inventory(ingredient3.getName(), LocalDateTime.of(2019, 11, 8, 0, 0), 10000, LocalDateTime.of(2020, 9, 23, 0, 0), LocalDateTime.of(2019, 11, 10, 0, 0));
        ingredient6.addInventory(inventory11);
        ingredientService.save(ingredient6);

        Ingredient ingredient7 = new Ingredient("Wheat", IngredientType.MALT, UnitIngredient.GRAMS, 0, "just some wheat malt");
        Inventory inventory12 = new Inventory(ingredient3.getName(), LocalDateTime.of(2019, 11, 8, 0, 0), 2000, LocalDateTime.of(2020, 9, 23, 0, 0), LocalDateTime.of(2019, 11, 10, 0, 0));
        ingredient7.addInventory(inventory12);
        ingredientService.save(ingredient7);


//
//        Ingredient ingredient2 = new Ingredient("Yeast north", IngredientType.YEAST, UnitIngredient.LB, 0, "Freshly");
//        Inventory inventory4 = new Inventory(ingredient2.getName(), LocalDateTime.of(2020, 3, 12, 12, 4), 153, LocalDateTime.of(2020, 6, 19, 20, 54), LocalDateTime.of(2020, 12, 29, 0, 0));
//        Inventory inventory5 = new Inventory(ingredient2.getName(), LocalDateTime.of(2020, 4, 14, 7, 42), 613, LocalDateTime.of(2021, 7, 1, 18, 20), null);
//        ingredient2.addInventory(inventory4);
//        ingredient2.addInventory(inventory5);
//        ingredientService.save(ingredient2);
//
//        Ingredient ingredient3 = new Ingredient("Icelandic hops", IngredientType.HOPS, UnitIngredient.LB, 0, "The best around");
//        Inventory inventory6 = new Inventory(ingredient3.getName(), LocalDateTime.of(2020, 4, 9, 12, 52), 6984, LocalDateTime.of(2020, 9, 23, 3, 48), LocalDateTime.of(2020, 5, 22, 0, 0));
//        ingredient3.addInventory(inventory6);
//        ingredientService.save(ingredient3);
//
//        Ingredient ingredient4 = new Ingredient("Sea salt", IngredientType.OTHER, UnitIngredient.LB, 0, "Bought in a local shop");
//        Inventory inventory7 = new Inventory(ingredient4.getName(), LocalDateTime.of(2020, 1, 23, 10, 11), 4314, LocalDateTime.of(2020, 11, 20, 10, 23), LocalDateTime.of(2020, 4, 9, 0, 0));
//        Inventory inventory8 = new Inventory(ingredient4.getName(), LocalDateTime.of(2020, 4, 1, 0, 50), 6424, LocalDateTime.of(2021, 2, 3, 13, 44), null);
//        ingredient4.addInventory(inventory7);
//        ingredient4.addInventory(inventory8);
//        ingredientService.save(ingredient4);

        //WATER
        BatchIngredient batchIngredient1 = new BatchIngredient(batch1.getId(), ingredient1.getName(), 25, 0, TechniqueType.BOIL);
        BatchIngredient batchIngredient2 = new BatchIngredient(batch2.getId(), ingredient1.getName(), 25, 0, TechniqueType.BOIL);
        BatchIngredient batchIngredient3 = new BatchIngredient(batch3.getId(), ingredient1.getName(), 20, 0, TechniqueType.BOIL);
        BatchIngredient batchIngredient4 = new BatchIngredient(batch4.getId(), ingredient1.getName(), 20, 0, TechniqueType.BOIL);
        ingredient1.addBatchIngredient(batchIngredient1);
        ingredient1.addBatchIngredient(batchIngredient2);
        ingredient1.addBatchIngredient(batchIngredient3);
        ingredient1.addBatchIngredient(batchIngredient4);
        batch1.addBatchIngredients(batchIngredient1);
        batch2.addBatchIngredients(batchIngredient2);
        batch3.addBatchIngredients(batchIngredient3);
        batch4.addBatchIngredients(batchIngredient4);

        //US-05
        BatchIngredient batchIngredient5 = new BatchIngredient(batch1.getId(), ingredient2.getName(), 1, 0, TechniqueType.DRY);
        BatchIngredient batchIngredient6 = new BatchIngredient(batch2.getId(), ingredient2.getName(), 1, 0, TechniqueType.DRY);
        BatchIngredient batchIngredient7 = new BatchIngredient(batch3.getId(), ingredient2.getName(), 1, 0, TechniqueType.DRY);
        BatchIngredient batchIngredient8 = new BatchIngredient(batch4.getId(), ingredient2.getName(), 1, 0, TechniqueType.DRY);
        ingredient2.addBatchIngredient(batchIngredient5);
        ingredient2.addBatchIngredient(batchIngredient6);
        ingredient2.addBatchIngredient(batchIngredient7);
        ingredient2.addBatchIngredient(batchIngredient8);
        batch1.addBatchIngredients(batchIngredient5);
        batch2.addBatchIngredients(batchIngredient6);
        batch3.addBatchIngredients(batchIngredient7);
        batch4.addBatchIngredients(batchIngredient8);

        //Mosaic
        BatchIngredient batchIngredient9 = new BatchIngredient(batch1.getId(), ingredient3.getName(), 100, 50, TechniqueType.BOIL);
        ingredient3.addBatchIngredient((batchIngredient9));
        batch1.addBatchIngredients(batchIngredient9);

        //Citra
        BatchIngredient batchIngredient10 = new BatchIngredient(batch1.getId(), ingredient4.getName(), 100, 30, TechniqueType.BOIL);
        ingredient4.addBatchIngredient((batchIngredient10));
        batch1.addBatchIngredients(batchIngredient10);

        //Citra
        BatchIngredient batchIngredient11 = new BatchIngredient(batch1.getId(), ingredient5.getName(), 50, 20, TechniqueType.BOIL);
        ingredient5.addBatchIngredient((batchIngredient11));
        batch1.addBatchIngredients(batchIngredient11);

        //BARLEY
        BatchIngredient batchIngredient12 = new BatchIngredient(batch1.getId(), ingredient6.getName(), 6000, 0, TechniqueType.BOIL);
        ingredient6.addBatchIngredient((batchIngredient12));
        batch1.addBatchIngredients(batchIngredient12);

        //WHEAT
        BatchIngredient batchIngredient13 = new BatchIngredient(batch1.getId(), ingredient7.getName(), 500, 0, TechniqueType.BOIL);
        ingredient7.addBatchIngredient((batchIngredient13));
        batch1.addBatchIngredients(batchIngredient13);

//        //YEAST NORTH
//        BatchIngredient batchIngredient5 = new BatchIngredient(batch2.getId(), ingredient2.getName(), 51, 12, TechniqueType.DRY);
//        BatchIngredient batchIngredient6 = new BatchIngredient(batch3.getId(), ingredient2.getName(), 128, 34, TechniqueType.DRY);
//        ingredient2.addBatchIngredient(batchIngredient5);
//        ingredient2.addBatchIngredient(batchIngredient6);
//        batch2.addBatchIngredients(batchIngredient5);
//        batch3.addBatchIngredients(batchIngredient6);
//
//        //ICELANDIC
//        BatchIngredient batchIngredient7 = new BatchIngredient(batch1.getId(), ingredient3.getName(), 65, 64, TechniqueType.DRY);
//        BatchIngredient batchIngredient8 = new BatchIngredient(batch3.getId(), ingredient3.getName(), 21, 86, TechniqueType.DRY);
//        BatchIngredient batchIngredient9 = new BatchIngredient(batch4.getId(), ingredient3.getName(), 12, 25, TechniqueType.DRY);
//        ingredient3.addBatchIngredient(batchIngredient7);
//        ingredient3.addBatchIngredient(batchIngredient8);
//        ingredient3.addBatchIngredient(batchIngredient9);
//        batch1.addBatchIngredients(batchIngredient7);
//        batch3.addBatchIngredients(batchIngredient8);
//        batch4.addBatchIngredients(batchIngredient9);
//
//        //SEA SALT
//        BatchIngredient batchIngredient10 = new BatchIngredient(batch1.getId(), ingredient4.getName(), 12, 86, TechniqueType.BOIL);
//        BatchIngredient batchIngredient11 = new BatchIngredient(batch3.getId(), ingredient4.getName(), 53, 33, TechniqueType.BOIL);
//        BatchIngredient batchIngredient12 = new BatchIngredient(batch3.getId(), ingredient4.getName(), 21, 75, TechniqueType.BOIL);
//        BatchIngredient batchIngredient13 = new BatchIngredient(batch4.getId(), ingredient4.getName(), 32, 12, TechniqueType.BOIL);
//        ingredient4.addBatchIngredient(batchIngredient10);
//        ingredient4.addBatchIngredient(batchIngredient11);
//        ingredient4.addBatchIngredient(batchIngredient12);
//        ingredient4.addBatchIngredient(batchIngredient13);
//        batch1.addBatchIngredients(batchIngredient10);
//        batch2.addBatchIngredients(batchIngredient11);
//        batch3.addBatchIngredients(batchIngredient12);
//        batch4.addBatchIngredients(batchIngredient13);

        batchService.save(batch1);
        batchService.save(batch2);
        batchService.save(batch3);
        batchService.save(batch4);

        ingredientService.save(ingredient1);
        ingredientService.save(ingredient2);
        ingredientService.save(ingredient3);
        ingredientService.save(ingredient4);
        ingredientService.save(ingredient5);
        ingredientService.save(ingredient6);
        ingredientService.save(ingredient7);

        ingredientService.setAmountOfProduct(ingredient1.getName(), batchIngredient1.getAmount() + batchIngredient2.getAmount() + batchIngredient3.getAmount() + batchIngredient4.getAmount());
        ingredientService.setAmountOfProduct(ingredient2.getName(), batchIngredient5.getAmount() + batchIngredient6.getAmount() + batchIngredient7.getAmount() + batchIngredient8.getAmount());
        ingredientService.setAmountOfProduct(ingredient3.getName(), batchIngredient9.getAmount());
        ingredientService.setAmountOfProduct(ingredient4.getName(), batchIngredient10.getAmount());
        ingredientService.setAmountOfProduct(ingredient5.getName(), batchIngredient11.getAmount());
        ingredientService.setAmountOfProduct(ingredient6.getName(), batchIngredient12.getAmount());
        ingredientService.setAmountOfProduct(ingredient7.getName(), batchIngredient13.getAmount());
    }
}
