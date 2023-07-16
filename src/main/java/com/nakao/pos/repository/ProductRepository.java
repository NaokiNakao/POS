package com.nakao.pos.repository;

import com.nakao.pos.model.Product;
import com.nakao.pos.util.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.nakao.pos.util.sql.ProductSQL.*;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class ProductRepository implements DAO<Product, String> {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Product> findAll() {
        return jdbc.query(SELECT_PRODUCTS, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public Optional<Product> findById(String id) {
        Product product;
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            product = jdbc.queryForObject(SELECT_PRODUCT, parameters, new BeanPropertyRowMapper<>(Product.class));
        }
        catch (EmptyResultDataAccessException e) {
            product = null;
        }

        return Optional.ofNullable(product);
    }

    @Override
    public Product insert(Product product) {
        product.setId(IdentifierGenerator.generateIdentifier(Product.ID_PATTERN));
        MapSqlParameterSource parameters = getSqlParameterSource(product);

        jdbc.update(INSERT_PRODUCT, parameters);

        return product;
    }

    @Override
    public Product update(String id, Product product) {
        product.setId(id);
        MapSqlParameterSource parameters = getSqlParameterSource(product);

        jdbc.update(UPDATE_PRODUCT, parameters);

        return product;
    }

    @Override
    public Boolean delete(String id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbc.update(DELETE_PRODUCT, parameters) == 1;
    }

    public Integer getProductCountByItemsExistence(String id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("product", id);

        return jdbc.queryForObject(PRODUCT_COUNT_BY_ITEMS, parameters, Integer.class);
    }

    private MapSqlParameterSource getSqlParameterSource(Product product) {
        return new MapSqlParameterSource()
                .addValue("id", product.getId())
                .addValue("name", product.getName())
                .addValue("category", product.getCategory())
                .addValue("stock", product.getStock())
                .addValue("minStock", product.getMinStock())
                .addValue("acquisitionCost", product.getAcquisitionCost())
                .addValue("sellingPrice", product.getSellingPrice());
    }

}
