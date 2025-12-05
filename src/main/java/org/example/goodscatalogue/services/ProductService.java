package org.example.goodscatalogue.services;

import org.example.goodscatalogue.models.Product;
import org.example.goodscatalogue.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Integer id) {
        return productRepository.findById(id);
    }

    public Product create(Product product) {
        return productRepository.create(product);
    }

    public Product update(Integer id, Product product) {
        return productRepository.updateById(id, product);
    }

    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    public void deleteAll() {
        productRepository.findAll().forEach(p -> productRepository.deleteById(p.getId()));
    }

    public List<Product> search(String query, Double minPrice, Double maxPrice, int page, int size) {
        return productRepository.findAll().stream()
                .filter(p -> query == null || p.getName().toLowerCase().contains(query.toLowerCase())
                        || (p.getDescription() != null && p.getDescription().toLowerCase().contains(query.toLowerCase())))
                .filter(p -> minPrice == null || p.getPrice() >= minPrice)
                .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }
    
    public long count(String query, Double minPrice, Double maxPrice) {
        return productRepository.findAll().stream()
                .filter(p -> query == null || p.getName().toLowerCase().contains(query.toLowerCase()))
                .filter(p -> minPrice == null || p.getPrice() >= minPrice)
                .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                .count();
    }
    
    public Product patch(Integer id, Map<String, Object> fields) {
        Product product = productRepository.findById(id);
        if (product == null) return null;

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Product.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, product, value);
            }
        });

        return productRepository.updateById(id, product);
    }
}
