package com.nakao.pos.util.sql;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */
public class CategorySQL {

    public static final String SELECT_CATEGORIES = "SELECT * FROM category";

    public static final String SELECT_CATEGORY = "SELECT * FROM category WHERE id = :id";

    public static final String INSERT_CATEGORY = "INSERT INTO category (id, name) VALUES (:id, :name)";

    public static final String UPDATE_CATEGORY = "UPDATE category SET name = :name WHERE id = :id";

    public static final String DELETE_CATEGORY = "DELETE FROM category WHERE id = :id";

}
