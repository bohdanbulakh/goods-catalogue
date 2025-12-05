package org.example.goodscatalogue.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Сутність товару")
public class Product extends Base {

    @Schema(description = "Назва товару", example = "iPhone 15")
    private String name;

    @Schema(description = "Детальний опис товару", example = "Смартфон Apple iPhone 15 128GB Black")
    private String description;

    @Schema(description = "Ціна товару", example = "29999.99")
    private Double price;

    @Schema(description = "Категорія, до якої належить товар")
    private Category category;
}