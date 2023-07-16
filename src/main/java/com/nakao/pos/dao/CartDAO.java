package com.nakao.pos.dao;

import com.nakao.pos.model.Cart;
import com.nakao.pos.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.nakao.pos.util.sql.CartSQL.*;
import static com.nakao.pos.util.sql.ProductSQL.PRODUCT_STOCK;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class CartDAO implements DAO<Cart, UUID> {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Cart> findAll() {
        return jdbc.query(SELECT_CARTS, new BeanPropertyRowMapper<>(Cart.class));
    }

    @Override
    public Optional<Cart> findById(UUID id) {
        Cart cart;
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            cart = jdbc.queryForObject(SELECT_CART, parameters, new BeanPropertyRowMapper<>(Cart.class));
        }
        catch (EmptyResultDataAccessException e) {
            cart = null;
        }

        return Optional.ofNullable(cart);
    }

    @Override
    public Cart insert(Cart cart) {
        cart.setId(UUID.randomUUID());
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", cart.getId())
                .addValue("employee", cart.getEmployee());

        jdbc.update(INSERT_CART, parameters);

        return cart;
    }

    @Override
    public Cart update(UUID id, Cart cart) {
        cart.setId(id);
        MapSqlParameterSource parameters = getSqlParameterSource(cart);

        jdbc.update(UPDATE_CART, parameters);

        return cart;
    }

    @Override
    public Boolean delete(UUID id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbc.update(DELETE_CART, parameters) == 1;
    }

    public CartItem addItem(CartItem cartItem) {
        cartItem.setCode(UUID.randomUUID());
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("code", cartItem.getCode())
                .addValue("product", cartItem.getProduct())
                .addValue("cart", cartItem.getCart());

        jdbc.update(ADD_ITEM, parameters);

        return cartItem;
    }

    public Boolean removeItem(String product, UUID cart) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("product", product)
                .addValue("cart", cart);

        return jdbc.update(REMOVE_ITEM, parameters) == 1;
    }

    public Integer getProductStock(String product) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("productId", product);

        return jdbc.queryForObject(PRODUCT_STOCK, parameters, Integer.class);
    }

    public void cartPriceUpdateProcedure(UUID id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        jdbc.update(UPDATE_NET, parameters);
        jdbc.update(UPDATE_TAX, parameters);
        jdbc.update(UPDATE_TOTAL, parameters);
    }

    public void processCart(UUID id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        jdbc.update(PROCESS_CART, parameters);
    }

    private MapSqlParameterSource getSqlParameterSource(Cart cart) {
        return new MapSqlParameterSource()
                .addValue("id", cart.getId())
                .addValue("date", cart.getDate())
                .addValue("net", cart.getNet())
                .addValue("tax", cart.getTax())
                .addValue("total", cart.getTotal())
                .addValue("paymentMethod", cart.getPaymentMethod())
                .addValue("status", cart.getStatus())
                .addValue("customer", cart.getCustomer())
                .addValue("employee", cart.getEmployee());
    }

}
