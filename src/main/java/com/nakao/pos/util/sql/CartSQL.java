package com.nakao.pos.util.sql;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public class CartSQL {

    public static final String SELECT_CARTS = "SELECT * FROM cart";

    public static final String SELECT_CART = "SELECT * FROM cart WHERE id = :id";

    public static final String INSERT_CART = "INSERT INTO cart (id, employee) VALUES (:id, :employee)";

    public static final String UPDATE_CART = "UPDATE cart SET date = :date, net = :net, tax = :tax, " +
            "total = :total, payment_method = :paymentMethod, status = :status, customer = :customer, " +
            "employee = :employee WHERE id = :id";

    public static final String DELETE_CART = "DELETE FROM cart WHERE id = :id";

    public static final String ADD_ITEM = "INSERT INTO cart_item (code, product, cart) VALUES (:code, :product, :cart)";

    public static final String REMOVE_ITEM = "DELETE FROM cart_item WHERE product = :product AND cart = :cart AND code = " +
            "(SELECT code FROM cart_item WHERE product = :product LIMIT 1)";

    public static final String UPDATE_NET = "UPDATE cart SET net = (SELECT SUM(p.selling_price) AS net " +
            "FROM cart c " +
            "JOIN cart_item ci ON c.id = ci.cart " +
            "JOIN product p ON ci.product = p.id " +
            "WHERE c.id = :id " +
            "GROUP BY c.id) " +
            "WHERE id = :id";

    public static final String UPDATE_TAX = "UPDATE cart SET tax = (net * 0.10) WHERE id = :id";

    public static final String UPDATE_TOTAL = "UPDATE cart SET total = (net + tax) WHERE id = :id";

}
