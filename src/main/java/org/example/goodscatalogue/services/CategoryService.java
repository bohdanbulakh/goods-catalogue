package org.example.goodscatalogue.services;

import org.example.goodscatalogue.models.Category;
import org.example.goodscatalogue.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private org.example.goodscatalogue.repositories.ProductRepository productRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Transactional
    public Category create(Category category) {
        return categoryRepository.create(category);
    }

    @Transactional
    public Category update(Integer id, Category category) {
        return categoryRepository.update(id, category);
    }

    @Transactional
    public void deleteById(Integer id) {
        List<Category> subcategories = categoryRepository.findByParentId(id);

        for (Category subCat : subcategories) {
            deleteById(subCat.getId());
        }

        productRepository.deleteByCategoryId(id);
        categoryRepository.deleteById(id);
    }
}