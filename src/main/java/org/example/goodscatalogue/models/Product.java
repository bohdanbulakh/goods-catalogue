package org.example.goodscatalogue.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Category category;
}
