package org.example.goodscatalogue.models;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class Category {
    private Integer id;
    private String name;
    private Category parent;
    private List<Product> products;
}
