package com.agh.database.brewingdatabaseapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "batch")
public class Batch {

    @Id
    private String id;
    @NotNull
    private String name;
    private Freezer freezer;
    private String style;
    private BatchType batchType;
    private LocalDate brewedDate;
    private LocalDate bottledDate;
    private List<Log> logs;
    private List<Mash> mashes;
    private List<BatchIngredients> batchIngredients;
}