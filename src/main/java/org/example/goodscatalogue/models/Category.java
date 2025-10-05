package org.example.goodscatalogue.models;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Category extends Base {
    private String name;
    private Category parent;
    private List<Product> products;
}
