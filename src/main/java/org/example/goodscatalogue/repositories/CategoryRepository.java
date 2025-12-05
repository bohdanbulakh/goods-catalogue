package org.example.goodscatalogue.repositories;

import org.example.goodscatalogue.models.Category;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepository {

    private final JdbcClient jdbcClient;

    public CategoryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Category> findAll() {
        return jdbcClient.sql("SELECT c.id, c.name, c.parent_id, p.name as parent_name " +
                        "FROM categories c LEFT JOIN categories p ON c.parent_id = p.id")
                .query(this::mapRowToCategory)
                .list();
    }

    public Category findById(Integer id) {
        return jdbcClient.sql("SELECT c.id, c.name, c.parent_id, p.name as parent_name " +
                        "FROM categories c LEFT JOIN categories p ON c.parent_id = p.id WHERE c.id = :id")
                .param("id", id)
                .query(this::mapRowToCategory)
                .optional()
                .orElse(null);
    }

    public Category create(Category category) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql("INSERT INTO categories (name, parent_id) VALUES (:name, :parentId)")
                .param("name", category.getName())
                .param("parentId", category.getParent() != null ? category.getParent().getId() : null)
                .update(keyHolder);

        category.setId(keyHolder.getKeyAs(Integer.class));
        return category;
    }

    public Category update(Integer id, Category category) {
        int updated = jdbcClient.sql("UPDATE categories SET name = :name, parent_id = :parentId WHERE id = :id")
                .param("name", category.getName())
                .param("parentId", category.getParent() != null ? category.getParent().getId() : null)
                .param("id", id)
                .update();

        return updated > 0 ? category : null;
    }

    public void deleteById(Integer id) {
        jdbcClient.sql("DELETE FROM categories WHERE id = :id")
                .param("id", id)
                .update();
    }

    private Category mapRowToCategory(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category(
                rs.getString("name"),
                null,
                new ArrayList<>()
        );
        category.setId(rs.getInt("id"));

        int parentId = rs.getInt("parent_id");
        if (!rs.wasNull()) {
            Category parent = new Category(rs.getString("parent_name"), null, null);
            parent.setId(parentId);
            category.setParent(parent);
        }
        return category;
    }

    public List<Category> findByParentId(Integer parentId) {
        return jdbcClient.sql("SELECT c.id, c.name, c.parent_id, p.name as parent_name " +
                        "FROM categories c LEFT JOIN categories p ON c.parent_id = p.id WHERE c.parent_id = :parentId")
                .param("parentId", parentId)
                .query(this::mapRowToCategory)
                .list();
    }
}