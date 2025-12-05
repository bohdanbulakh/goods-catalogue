package org.example.goodscatalogue.repositories;

import org.example.goodscatalogue.models.Category;
import org.example.goodscatalogue.models.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> {
        Product product = new Product(
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price"),
                null
        );
        product.setId(rs.getInt("id"));

        int catId = rs.getInt("category_id");
        if (!rs.wasNull()) {
            Category cat = new Category(rs.getString("category_name"), null, null);
            cat.setId(catId);
            product.setCategory(cat);
        }
        return product;
    };

    public List<Product> findAll() {
        String sql = "SELECT p.*, c.name as category_name FROM products p LEFT JOIN categories c ON p.category_id = c.id";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public Product findById(Integer id) {
        String sql = "SELECT p.*, c.name as category_name FROM products p LEFT JOIN categories c ON p.category_id = c.id WHERE p.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, productRowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Product create(Product product) {
        String sql = "INSERT INTO products (name, description, price, category_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            if (product.getCategory() != null) {
                ps.setInt(4, product.getCategory().getId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            return ps;
        }, keyHolder);

        product.setId(keyHolder.getKey().intValue());
        return product;
    }

    public Product updateById(Integer id, Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, category_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql,
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                (product.getCategory() != null) ? product.getCategory().getId() : null,
                id);
        return rows > 0 ? product : null;
    }

    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM products WHERE id = ?", id);
    }

    public void deleteByCategoryId(Integer categoryId) {
        String sql = "DELETE FROM products WHERE category_id = ?";
        jdbcTemplate.update(sql, categoryId);
    }
}