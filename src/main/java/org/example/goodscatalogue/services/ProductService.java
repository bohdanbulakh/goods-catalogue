package org.example.goodscatalogue.services;

import org.example.goodscatalogue.models.Product;
import org.example.goodscatalogue.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
