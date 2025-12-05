package org.example.goodscatalogue.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.goodscatalogue.models.Product;
import org.example.goodscatalogue.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Товари", description = "API для управління товарами")
public class ProductRestController {

    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Отримати список товарів",
            description = "Повертає список всіх товарів з можливістю фільтрації за назвою, ціною та пагінації."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успішне отримання списку",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @Parameter(description = "Пошуковий запит (назва або опис)") @RequestParam(required = false) String query,
            @Parameter(description = "Мінімальна ціна") @RequestParam(required = false) Double minPrice,
            @Parameter(description = "Максимальна ціна") @RequestParam(required = false) Double maxPrice,
            @Parameter(description = "Номер сторінки (починаючи з 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Кількість елементів на сторінці") @RequestParam(defaultValue = "10") int size) {

        List<Product> products = productService.search(query, minPrice, maxPrice, page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(
            summary = "Отримати товар за ID",
            description = "Повертає повну інформацію про товар за його унікальним ідентифікатором."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар знайдено",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Товар не знайдено", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "Ідентифікатор товару") @PathVariable Integer id) {
        Product product = productService.getById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Operation(
            summary = "Створити новий товар",
            description = "Створює новий товар на основі переданих даних."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Товар успішно створено",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дані", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Дані нового товару") @RequestBody Product product) {
        product.setId(null);
        Product created = productService.create(product);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Оновити товар повністю",
            description = "Повністю оновлює інформацію про товар за вказаним ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар оновлено",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Товар для оновлення не знайдено", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @Parameter(description = "Ідентифікатор товару") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Оновлені дані товару") @RequestBody Product product) {
        Product updated = productService.update(id, product);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(
            summary = "Часткове оновлення товару",
            description = "Оновлює лише передані поля товару (PATCH запит)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар оновлено",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Товар не знайдено", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(
            @Parameter(description = "Ідентифікатор товару") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Поля для оновлення (ключ-значення)") @RequestBody Map<String, Object> fields) {
        Product patched = productService.patch(id, fields);
        if (patched == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(patched, HttpStatus.OK);
    }

    @Operation(
            summary = "Видалити товар",
            description = "Видаляє товар з каталогу за його ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Товар успішно видалено", content = @Content),
            @ApiResponse(responseCode = "404", description = "Товар не знайдено", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Ідентифікатор товару") @PathVariable Integer id) {
        Product existing = productService.getById(id);
        if (existing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}