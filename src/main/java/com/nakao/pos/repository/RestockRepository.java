package com.nakao.pos.repository;

import com.nakao.pos.model.Restock;
import com.nakao.pos.util.enumeration.RestockStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.nakao.pos.util.sql.ProductSQL.UPDATE_PRODUCT_STOCK;
import static com.nakao.pos.util.sql.RestockSQL.*;

/**
 * @author Naoki Nakao on 7/14/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class RestockRepository implements DAO<Restock, UUID> {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Restock> findAll() {
        return jdbc.query(SELECT_RESTOCKS, new BeanPropertyRowMapper<>(Restock.class));
    }

    @Override
    public Optional<Restock> findById(UUID id) {
        Restock restock;
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            restock = jdbc.queryForObject(SELECT_RESTOCK, parameters, new BeanPropertyRowMapper<>(Restock.class));
        }
        catch (EmptyResultDataAccessException e) {
            restock = null;
        }

        return Optional.ofNullable(restock);
    }

    @Override
    public Restock insert(Restock restock) {
        restock.setId(UUID.randomUUID());
        MapSqlParameterSource parameters = getSqlParameterSource(restock);

        jdbc.update(INSERT_RESTOCK, parameters);

        return restock;
    }

    @Override
    public Restock update(UUID id, Restock restock) {
        restock.setId(id);
        MapSqlParameterSource parameters = getSqlParameterSource(restock);

        jdbc.update(UPDATE_RESTOCK, parameters);

        return restock;
    }

    @Override
    public Boolean delete(UUID id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbc.update(DELETE_RESTOCK, parameters) == 1;
    }

    public void updateStatus(UUID id, RestockStatus status) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("status", status.getStatus());

        jdbc.update(UPDATE_STATUS, parameters);
    }

    public void updateProductStock(String productId, Integer quantity) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("productId", productId)
                .addValue("quantity", quantity);

        jdbc.update(UPDATE_PRODUCT_STOCK, parameters);
    }

    private MapSqlParameterSource getSqlParameterSource(Restock restock) {
        return new MapSqlParameterSource()
                .addValue("id", restock.getId())
                .addValue("deliveryDate", restock.getDeliveryDate())
                .addValue("product", restock.getProduct())
                .addValue("productQuantity", restock.getProductQuantity())
                .addValue("supplier", restock.getSupplier())
                .addValue("status", restock.getStatus());
    }

}
