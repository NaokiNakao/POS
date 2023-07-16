package com.nakao.pos.util.sql;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public class StoreOrderSQL {

    public static final String SELECT_ORDERS = "SELECT * FROM store_order";

    public static final String SELECT_ORDER = "SELECT * FROM store_order WHERE id = :id";

    public static final String INSERT_ORDER = "INSERT INTO store_order (id, employee) VALUES (:id, :employee)";

    public static final String UPDATE_ORDER = "UPDATE store_order SET date = :date, net = :net, tax = :tax, " +
            "total = :total, payment_method = :paymentMethod, status = :status, customer = :customer, " +
            "employee = :employee WHERE id = :id";

    public static final String DELETE_ORDER = "DELETE FROM store_order WHERE id = :id";

    public static final String ADD_ITEM = "INSERT INTO order_item (code, product, store_order) VALUES (:code, :product, :storeOrder)";

    public static final String REMOVE_ITEM = "DELETE FROM order_item WHERE product = :product AND store_order = :storeOrder " +
            "AND code = (SELECT code FROM order_item WHERE product = :product LIMIT 1)";

    public static final String UPDATE_NET = "UPDATE store_order SET net = (SELECT SUM(p.selling_price) AS net " +
            "FROM store_order so " +
            "JOIN order_item oi ON so.id = oi.store_order " +
            "JOIN product p ON oi.product = p.id " +
            "WHERE so.id = :id " +
            "GROUP BY so.id) " +
            "WHERE id = :id";

    public static final String UPDATE_TAX = "UPDATE store_order SET tax = (net * 0.10) WHERE id = :id";

    public static final String UPDATE_TOTAL = "UPDATE store_order SET total = (net + tax) WHERE id = :id";

    public static final String PROCESS_ORDER = "UPDATE store_order SET date = CURRENT_DATE, status = 'PROCESSED' WHERE id = :id";

}
