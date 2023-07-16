package com.nakao.pos.repository;

import com.nakao.pos.model.StoreOrder;
import com.nakao.pos.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.nakao.pos.util.sql.StoreOrderSQL.*;
import static com.nakao.pos.util.sql.ProductSQL.PRODUCT_STOCK;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class StoreOrderRepository implements DAO<StoreOrder, UUID> {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<StoreOrder> findAll() {
        return jdbc.query(SELECT_ORDERS, new BeanPropertyRowMapper<>(StoreOrder.class));
    }

    @Override
    public Optional<StoreOrder> findById(UUID id) {
        StoreOrder storeOrder;
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            storeOrder = jdbc.queryForObject(SELECT_ORDER, parameters, new BeanPropertyRowMapper<>(StoreOrder.class));
        }
        catch (EmptyResultDataAccessException e) {
            storeOrder = null;
        }

        return Optional.ofNullable(storeOrder);
    }

    @Override
    public StoreOrder insert(StoreOrder storeOrder) {
        storeOrder.setId(UUID.randomUUID());
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", storeOrder.getId())
                .addValue("employee", storeOrder.getEmployee());

        jdbc.update(INSERT_ORDER, parameters);

        return storeOrder;
    }

    @Override
    public StoreOrder update(UUID id, StoreOrder storeOrder) {
        storeOrder.setId(id);
        MapSqlParameterSource parameters = getSqlParameterSource(storeOrder);

        jdbc.update(UPDATE_ORDER, parameters);

        return storeOrder;
    }

    @Override
    public Boolean delete(UUID id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbc.update(DELETE_ORDER, parameters) == 1;
    }

    public OrderItem addItem(OrderItem orderItem) {
        orderItem.setCode(UUID.randomUUID());
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("code", orderItem.getCode())
                .addValue("product", orderItem.getProduct())
                .addValue("storeOrder", orderItem.getStoreOrder());

        jdbc.update(ADD_ITEM, parameters);

        return orderItem;
    }

    public Boolean removeItem(String product, UUID storeOrder) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("product", product)
                .addValue("storeOrder", storeOrder);

        return jdbc.update(REMOVE_ITEM, parameters) == 1;
    }

    public Integer getProductStock(String product) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("productId", product);

        return jdbc.queryForObject(PRODUCT_STOCK, parameters, Integer.class);
    }

    public void orderPriceUpdateProcedure(UUID id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        jdbc.update(UPDATE_NET, parameters);
        jdbc.update(UPDATE_TAX, parameters);
        jdbc.update(UPDATE_TOTAL, parameters);
    }

    public void processOrder(UUID id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        jdbc.update(PROCESS_ORDER, parameters);
    }

    private MapSqlParameterSource getSqlParameterSource(StoreOrder storeOrder) {
        return new MapSqlParameterSource()
                .addValue("id", storeOrder.getId())
                .addValue("date", storeOrder.getDate())
                .addValue("net", storeOrder.getNet())
                .addValue("tax", storeOrder.getTax())
                .addValue("total", storeOrder.getTotal())
                .addValue("paymentMethod", storeOrder.getPaymentMethod())
                .addValue("status", storeOrder.getStatus())
                .addValue("customer", storeOrder.getCustomer())
                .addValue("employee", storeOrder.getEmployee());
    }

}
