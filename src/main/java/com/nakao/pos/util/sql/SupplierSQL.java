package com.nakao.pos.util.sql;

/**
 * @author Naoki Nakao on 7/14/2023
 * @project POS
 */
public class SupplierSQL {

    public static final String SELECT_SUPPLIERS = "SELECT * FROM supplier";

    public static final String SELECT_SUPPLIER = "SELECT * FROM supplier WHERE id = :id";

    public static final String INSERT_SUPPLIER = "INSERT INTO supplier (id, name, address, contact) VALUES(:id, " +
            ":name, :address, :contact)";

    public static final String UPDATE_SUPPLIER = "UPDATE supplier SET name = :name, address = :address, " +
            "contact = :contact WHERE id = :id";

    public static final String DELETE_SUPPLIER = "DELETE FROM supplier WHERE id = :id";

    public static final String SUPPLIER_COUNT_BY_RESTOCK = "SELECT COUNT (*) FROM restock WHERE supplier = :supplier";

}
