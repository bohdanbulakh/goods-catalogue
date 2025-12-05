package org.example.goodscatalogue.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Сутність категорії")
public class Category extends Base {

    @Schema(description = "Назва категорії", example = "Смартфони")
    private String name;

    @Schema(description = "Батьківська категорія (якщо є)")
    private Category parent;

    @Schema(description = "Список товарів у цій категорії")
    private List<Product> products;
}