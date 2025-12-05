package org.example.goodscatalogue.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@NamedQuery(
        name = "Product.findByExactName",
        query = "SELECT p FROM Product p WHERE p.name = ?1"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сутність товару")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Назва товару", example = "iPhone 15")
    private String name;

    @Schema(description = "Детальний опис товару")
    private String description;

    @Column(nullable = false)
    @Schema(description = "Ціна товару")
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @Schema(description = "Категорія товару")
    private Category category;
}