package org.example.goodscatalogue.services;

import org.example.goodscatalogue.models.Category;
import org.example.goodscatalogue.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Integer id) {
        return categoryRepository.findById(id);
    }

    public Category create(Category category) {
        return categoryRepository.create(category);
    }

    public Category update(Integer id, Category category) {
        return categoryRepository.updateById(id, category);
    }

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }
}
