package com.nakao.pos.dao;

import com.nakao.pos.model.Product;
import com.nakao.pos.util.IdentifierGenerator;
import com.nakao.pos.util.mapper.ProductRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.nakao.pos.util.sql.ProductSQL.*;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class ProductDAO implements DAO<Product, String> {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Product> findAll() {
        return jdbc.query(SELECT_PRODUCTS, new ProductRowMapper());
    }

    @Override
    public Optional<Product> findById(String id) {
        Product product;
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            product = jdbc.queryForObject(SELECT_PRODUCT, parameters, new ProductRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            product = null;
        }

        return Optional.ofNullable(product);
    }

    @Override
    public Product insert(Product product) {
        MapSqlParameterSource parameters = getSqlParameterSource(product);
        String productId = IdentifierGenerator.generateIdentifier(Product.ID_PATTERN);
        parameters.addValue("id", productId);

        jdbc.update(INSERT_PRODUCT, parameters);

        product.setId(productId);

        return product;
    }

    @Override
    public Product update(String id, Product product) {
        MapSqlParameterSource parameters = getSqlParameterSource(product);
        parameters.addValue("id", id);

        jdbc.update(UPDATE_PRODUCT, parameters);

        product.setId(id);

        return product;
    }

    @Override
    public Boolean delete(String id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbc.update(DELETE_PRODUCT, parameters) == 1;
    }

    private MapSqlParameterSource getSqlParameterSource(Product product) {
        return new MapSqlParameterSource()
                .addValue("name", product.getName())
                .addValue("category", product.getCategory())
                .addValue("stock", product.getStock())
                .addValue("minStock", product.getMinStock())
                .addValue("acquisitionCost", product.getAcquisitionCost())
                .addValue("sellingPrice", product.getSellingPrice());
    }

}
