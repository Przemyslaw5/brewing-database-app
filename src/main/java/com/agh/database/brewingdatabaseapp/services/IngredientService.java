package com.agh.database.brewingdatabaseapp.services;

import com.agh.database.brewingdatabaseapp.model.Batch;
import com.agh.database.brewingdatabaseapp.model.BatchIngredient;
import com.agh.database.brewingdatabaseapp.model.Ingredient;
import com.agh.database.brewingdatabaseapp.model.Inventory;
import com.agh.database.brewingdatabaseapp.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class IngredientService implements MongoService<Ingredient, String> {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {

        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Set<Ingredient> findAll() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        return ingredients;
    }

    public List<BatchIngredient> prepareBatchIngredientsList(){
        List<BatchIngredient> amounts = new LinkedList<>();
        Set<Ingredient> ingredients = this.findAll();
        for(Ingredient ingredient : ingredients){
            amounts.add(new BatchIngredient(ingredient.getName(), 0));
        }
        return amounts;
    }

    public List<BatchIngredient> getBatchIngredientsList(List<BatchIngredient> batchIngredients, Batch batch){
        List<BatchIngredient> batchIngredientList = new LinkedList<>();
        for(BatchIngredient b : batchIngredients){
            if(b.getAmount() != 0){
                b.setBatchID(batch.getId());
                batchIngredientList.add(b);
            }
        }
        return batchIngredientList;
    }

    @Override
    public Ingredient findById(String s) {
        return ingredientRepository.findById(s).orElse(null);
    }

    public Ingredient findByName(String s) {
        return ingredientRepository.findByName(s);
    }

    @Override
    public Ingredient save(Ingredient object) {
        return ingredientRepository.save(object);
    }

    @Override
    public void delete(Ingredient object) {
        ingredientRepository.delete(object);
    }

    @Override
    public void deleteById(String s) {
        ingredientRepository.deleteById(s);
    }

    @Override
    public void deleteAll() {
        ingredientRepository.deleteAll();
    }

    public void setAmountOfProduct(String name, double amount){
        if(amount <= 0.0) return;
        Ingredient ingredient = ingredientRepository.findByName(name);
        ingredient.setUnitsInStock(ingredient.getUnitsInStock() - amount);
        List<Inventory> inventories = ingredient.getInventories();
        for (Inventory inventory: inventories){
            if(inventory.getAmountAvailable() > 0){
                if(inventory.getAmountAvailable() > amount){
                    inventory.setAmountAvailable(inventory.getAmountAvailable() - amount);
                    amount = 0;
                }
                else{
                    double tmp = inventory.getAmountAvailable();
                    inventory.setAmountAvailable(0);
                    amount -= tmp;
                }
                if("".equals(inventory.getOpened())){
                    inventory.setOpened(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                }
                if(amount == 0){
                    ingredient.setInventories(inventories);
                    ingredientRepository.save(ingredient);
                    return;
                }
            }
        }
    }

    public boolean checkAmounts(List<BatchIngredient> batchIngredients){
        for (BatchIngredient batchIngredient : batchIngredients) {
            if (batchIngredient.getAmount() > ingredientRepository.findByName(batchIngredient.getIngredientName()).getUnitsInStock()) {
                return true;
            }
        }
        return false;
    }

    public void changeAmountsInStock(List<BatchIngredient> batchIngredients){
        for (BatchIngredient batchIngredient : batchIngredients) {
            setAmountOfProduct(batchIngredient.getIngredientName(), batchIngredient.getAmount());
        }
    }

    public List<Double> getAmountAvailable(List<BatchIngredient> batchIngredients){
        List<Double> list = new LinkedList<>();
        for(BatchIngredient batchIngredient : batchIngredients){
            list.add(ingredientRepository.findByName(batchIngredient.getIngredientName()).getUnitsInStock());
        }
        return list;
    }
}
