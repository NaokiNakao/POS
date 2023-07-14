package com.nakao.pos.dao;

import com.nakao.pos.model.Category;
import com.nakao.pos.util.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.nakao.pos.util.sql.CategorySQL.INSERT_CATEGORY;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class CategoryDAO implements DAO<Category, String> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll() {
        return null;
    }

    @Override
    public Optional<Category> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Category insert(Category category) {
        Category newCategory = Category.builder()
                .id(IdentifierGenerator.generateIdentifier(Category.ID_PATTERN))
                .name(category.getName())
                .build();

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", newCategory.getId())
                .addValue("name", newCategory.getName());

        jdbcTemplate.update(INSERT_CATEGORY, parameters);

        return newCategory;
    }

    @Override
    public Category update(String id, Category category) {
        return null;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }

}
