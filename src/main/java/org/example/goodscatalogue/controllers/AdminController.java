package org.example.goodscatalogue.controllers;

import org.example.goodscatalogue.models.Category;
import org.example.goodscatalogue.models.Product;
import org.example.goodscatalogue.services.CategoryService;
import org.example.goodscatalogue.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public String adminHome() {
        return "admin/categories";
    }

    /* ---------- CATEGORY ---------- */

    @GetMapping("/categories")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "admin/categories";
    }

    @PostMapping("/categories")
    public String createCategory(@RequestParam String name) {
        categoryService.create(new Category(name, null, new ArrayList<>()));
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{id}/delete")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }

    /* ---------- PRODUCT ---------- */

    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        return "admin/products";
    }

    @PostMapping("/products")
    public String createProduct(@RequestParam String name,
                                @RequestParam String description,
                                @RequestParam Double price) {
        productService.create(new Product(name, description, price, null));
        return "redirect:/admin/products";
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }
}
