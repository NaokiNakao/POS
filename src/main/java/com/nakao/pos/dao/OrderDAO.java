package com.nakao.pos.dao;

import com.nakao.pos.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.nakao.pos.util.sql.OrderSQL.*;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class OrderDAO implements DAO<Order, UUID> {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Order> findAll() {
        return jdbc.query(SELECT_ORDERS, new BeanPropertyRowMapper<>(Order.class));
    }

    @Override
    public Optional<Order> findById(UUID id) {
        Order order;
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            order = jdbc.queryForObject(SELECT_ORDER, parameters, new BeanPropertyRowMapper<>(Order.class));
        }
        catch (EmptyResultDataAccessException e) {
            order = null;
        }

        return Optional.ofNullable(order);
    }

    @Override
    public Order insert(Order order) {
        order.setId(UUID.randomUUID());
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", order.getId())
                .addValue("employee", order.getEmployee());

        jdbc.update(INSERT_ORDER, parameters);

        return order;
    }

    @Override
    public Order update(UUID id, Order order) {
        order.setId(id);
        MapSqlParameterSource parameters = getSqlParameterSource(order);

        jdbc.update(UPDATE_ORDER, parameters);

        return order;
    }

    @Override
    public Boolean delete(UUID id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbc.update(DELETE_ORDER, parameters) == 1;
    }

    private MapSqlParameterSource getSqlParameterSource(Order order) {
        return new MapSqlParameterSource()
                .addValue("id", order.getId())
                .addValue("date", order.getDate())
                .addValue("net", order.getNet())
                .addValue("tax", order.getTax())
                .addValue("total", order.getTotal())
                .addValue("paymentMethod", order.getPaymentMethod())
                .addValue("status", order.getStatus())
                .addValue("customer", order.getCustomer())
                .addValue("employee", order.getEmployee());
    }

}
