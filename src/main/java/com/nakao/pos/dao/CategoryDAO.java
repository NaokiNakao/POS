package com.nakao.pos.dao;

import com.nakao.pos.model.Category;
import com.nakao.pos.util.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.nakao.pos.util.sql.CategorySQL.*;
import static com.nakao.pos.util.sql.ProductSQL.PRODUCT_COUNT_BY_CATEGORY;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class CategoryDAO implements DAO<Category, String> {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Category> findAll() {
        return jdbc.query(SELECT_CATEGORIES, new BeanPropertyRowMapper<>(Category.class));
    }

    @Override
    public Optional<Category> findById(String id) {
        Category category;
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            category = jdbc.queryForObject(SELECT_CATEGORY, parameters, new BeanPropertyRowMapper<>(Category.class));
        }
        catch (EmptyResultDataAccessException e) {
            category = null;
        }

        return Optional.ofNullable(category);
    }

    @Override
    public Category insert(Category category) {
        category.setId(IdentifierGenerator.generateIdentifier(Category.ID_PATTERN));
        MapSqlParameterSource parameters = getSqlParameterSource(category);

        jdbc.update(INSERT_CATEGORY, parameters);

        return category;
    }

    @Override
    public Category update(String id, Category category) {
        category.setId(id);
        MapSqlParameterSource parameters = getSqlParameterSource(category);

        jdbc.update(UPDATE_CATEGORY, parameters);

        return category;
    }

    @Override
    public Boolean delete(String id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbc.update(DELETE_CATEGORY, parameters) == 1;
    }

    public Integer getProductCountByCategoryId(String categoryId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("categoryId", categoryId);

        return jdbc.queryForObject(PRODUCT_COUNT_BY_CATEGORY, parameters, Integer.class);
    }

    private MapSqlParameterSource getSqlParameterSource(Category category) {
        return new MapSqlParameterSource()
                .addValue("id", category.getId())
                .addValue("name", category.getName());
    }

}
