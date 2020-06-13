package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "batch")
public class Batch {

    @Id
    private String id;
    @Size(min=5, max=20, message = "Name must be between 5 and 20 characters.")
    private String name;
    private Freezer freezer;
    @Size(min=3, max=15, message = "Name must be between 3 and 15 characters.")
    private String style;
    @NotNull
    private BatchType batchType;
    @NotNull(message = "You mast set brewed date")
    private String brewedDate;
    @NotNull(message = "You mast set bottled date")
    private String bottledDate;
    private List<Log> logs;
    private List<Mash> mashes;
    private List<BatchIngredient> batchIngredients;

    public Batch(String name, Freezer freezer, String style, BatchType batchType, LocalDateTime brewedDate, LocalDateTime bottledDate) {
        this.name = name;
        this.freezer = freezer;
        this.style = style;
        this.batchType = batchType;
        this.brewedDate = brewedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.bottledDate = bottledDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));;
    }

    public void addLog(Log log){
        if(this.logs == null){
            this.logs = new LinkedList<>();
        }
        this.logs.add(log);
    }

    public void addMash(Mash mash){
        if(this.mashes == null){
            this.mashes = new LinkedList<>();
        }
        this.mashes.add(mash);
    }

    public void addBatchIngredients(BatchIngredient batchIngredient){
        if(this.batchIngredients == null){
            this.batchIngredients = new LinkedList<>();
        }
        this.batchIngredients.add(batchIngredient);
    }
}