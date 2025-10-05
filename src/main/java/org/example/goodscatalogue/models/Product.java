package org.example.goodscatalogue.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends Base {
    private String name;
    private String description;
    private Double price;
    private Category category;
}
