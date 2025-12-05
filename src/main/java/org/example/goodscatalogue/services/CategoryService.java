package org.example.goodscatalogue.services;

import org.example.goodscatalogue.models.Category;
import org.example.goodscatalogue.repositories.CategoryRepository;
import org.example.goodscatalogue.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Transactional
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Integer id, Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteById(Integer id) {
        List<Category> subCategories = categoryRepository.findByParentId(id);
        for (Category sub : subCategories) {
            deleteById(sub.getId());
        }

        productRepository.deleteByCategoryId(id);
        categoryRepository.deleteById(id);
    }
}