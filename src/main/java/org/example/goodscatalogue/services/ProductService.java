package org.example.goodscatalogue.services;

import org.example.goodscatalogue.models.Product;
import org.example.goodscatalogue.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Integer id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    public List<Product> search(String query, Double minPrice, Double maxPrice, int page, int size) {
        List<Product> result = productRepository.searchProducts(query, minPrice, maxPrice);

        return result.stream()
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Transactional
    public Product patch(Integer id, Map<String, Object> fields) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return null;

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Product.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, product, value);
            }
        });

        return productRepository.save(product);
    }
}