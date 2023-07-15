package com.nakao.pos.util.sql;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public class OrderSQL {

    public static final String SELECT_ORDERS = "SELECT * FROM item_order";

    public static final String SELECT_ORDER = "SELECT * FROM item_order WHERE id = :id";

    public static final String INSERT_ORDER = "INSERT INTO item_order (id, employee) VALUES (:id, :employee)";

    public static final String UPDATE_ORDER = "UPDATE item_order SET date = :date, net = :net, tax = :tax, " +
            "total = :total, payment_method = :paymentMethod, status = :status, customer = :customer, " +
            "employee = :employee WHERE id = :id";

    public static final String DELETE_ORDER = "DELETE FROM item_order WHERE id = :id";

}
