package com.nakao.pos.util.sql;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */
public class ProductSQL {

    public static final String SELECT_PRODUCTS = "SELECT * FROM product";

    public static final String SELECT_PRODUCT = "SELECT * FROM product WHERE id = :id";

    public static final String INSERT_PRODUCT = "INSERT INTO product (id, name, category, stock, min_stock, " +
            "acquisition_cost, selling_price) VALUES (:id, :name, :category, :stock, :minStock, :acquisitionCost, " +
            ":sellingPrice)";

    public static final String UPDATE_PRODUCT = "UPDATE product SET name = :name, category = :category, " +
            "stock = :stock, min_stock = :minStock, acquisition_cost = :acquisitionCost, " +
            "selling_price = :sellingPrice WHERE id = :id";

    public static final String DELETE_PRODUCT = "DELETE FROM product WHERE id = :id";

}
