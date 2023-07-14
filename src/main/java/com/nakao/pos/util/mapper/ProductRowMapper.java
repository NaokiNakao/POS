package com.nakao.pos.util.mapper;

import com.nakao.pos.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */
public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getString("id"));
        product.setName(rs.getString("name"));
        product.setCategory(rs.getString("category"));
        product.setStock(rs.getInt("stock"));
        product.setMinStock(rs.getInt("min_stock"));
        product.setAcquisitionCost(rs.getBigDecimal("acquisition_cost"));
        product.setSellingPrice(rs.getBigDecimal("selling_price"));
        return product;
    }

}
