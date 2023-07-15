package com.nakao.pos.util.sql;

/**
 * @author Naoki Nakao on 7/14/2023
 * @project POS
 */
public class RestockSQL {

    public static final String SELECT_RESTOCKS = "SELECT * FROM restock";

    public static final String SELECT_RESTOCK = "SELECT * FROM restock WHERE id = :id";

    public static final String INSERT_RESTOCK = "INSERT INTO restock (id, delivery_date, product, " +
            "product_quantity, supplier, status) VALUES (:id, :deliveryDate, :product, :productQuantity, :supplier, :status)";

    public static final String UPDATE_RESTOCK = "UPDATE restock SET delivery_date = :deliveryDate, " +
            "product = :product, product_quantity = :productQuantity, supplier = :supplier, status = :status " +
            "WHERE id = :id";

    public static final String DELETE_RESTOCK = "DELETE FROM restock WHERE id = :id";

    public static final String UPDATE_STATUS = "UPDATE restock SET status = :status WHERE id = :id";

    public static final String UPDATE_PRODUCT_STOCK = "UPDATE product SET stock = stock + :quantity WHERE id = :productId";

}
