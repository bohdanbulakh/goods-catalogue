package org.example.goodscatalogue.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.goodscatalogue.models.Category;
import org.example.goodscatalogue.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Категорії", description = "API для управління категоріями товарів")
public class CategoryRestController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Отримати всі категорії", description = "Повертає повний список категорій.")
    @ApiResponse(responseCode = "200", description = "Успішна операція",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class)))
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Отримати категорію за ID", description = "Повертає деталі категорії за ідентифікатором.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категорію знайдено",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "404", description = "Категорію не знайдено", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(
            @Parameter(description = "Ідентифікатор категорії") @PathVariable Integer id) {
        Category category = categoryService.getById(id);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Operation(summary = "Створити категорію", description = "Створює нову категорію товарів.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Категорію створено",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дані", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Category> createCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Дані нової категорії") @RequestBody Category category) {
        category.setId(null);
        Category created = categoryService.create(category);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Оновити категорію", description = "Оновлює інформацію про існуючу категорію.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категорію оновлено",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "404", description = "Категорію не знайдено", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @Parameter(description = "Ідентифікатор категорії") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Оновлені дані категорії") @RequestBody Category category) {
        Category updated = categoryService.update(id, category);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Видалити категорію", description = "Видаляє категорію за її ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Категорію видалено", content = @Content),
            @ApiResponse(responseCode = "404", description = "Категорію не знайдено", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "Ідентифікатор категорії") @PathVariable Integer id) {
        Category existing = categoryService.getById(id);
        if (existing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}