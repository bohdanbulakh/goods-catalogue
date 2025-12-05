package org.example.goodscatalogue.controllers;

import org.example.goodscatalogue.components.RequestTimer;
import org.example.goodscatalogue.models.Category;
import org.example.goodscatalogue.models.Product;
import org.example.goodscatalogue.services.CategoryService;
import org.example.goodscatalogue.services.ProductService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    private ObjectFactory<RequestTimer> timerFactory;

    @Autowired
    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    private void addCommonAttributes(Model model) {
        model.addAttribute("timer", timerFactory.getObject().getTimestamp());
    }

    @GetMapping
    public String adminHome() {
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        addCommonAttributes(model);
        return "admin/categories";
    }

    @GetMapping("/categories/new")
    public String newCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.getAll());
        return "admin/category-edit";
    }

    @GetMapping("/categories/{id}/edit")
    public String editCategoryForm(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        model.addAttribute("categories", categoryService.getAll());
        return "admin/category-edit";
    }

    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute Category category,
                               @RequestParam(required = false) Integer parentId) {
        if (parentId != null) {
            category.setParent(categoryService.getById(parentId));
        } else {
            category.setParent(null);
        }

        if (category.getId() == null) {
            categoryService.create(category);
        } else {
            Category old = categoryService.getById(category.getId());
            if (old != null) {
                category.setProducts(old.getProducts());
                category.setSubCategories(old.getSubCategories());
            }
            categoryService.update(category.getId(), category);
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{id}/delete")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        addCommonAttributes(model);
        return "admin/products";
    }

    @GetMapping("/products/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "admin/product-edit";
    }

    @GetMapping("/products/{id}/edit")
    public String editProductForm(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getById(id));
        model.addAttribute("categories", categoryService.getAll());
        return "admin/product-edit";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam(required = false) Integer categoryId) {
        if (categoryId != null) {
            product.setCategory(categoryService.getById(categoryId));
        } else {
            product.setCategory(null);
        }

        if (product.getId() == null) {
            productService.create(product);
        } else {
            productService.update(product.getId(), product);
        }
        return "redirect:/admin/products";
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }
}