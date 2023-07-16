package com.nakao.pos.repository;

import com.nakao.pos.model.Supplier;
import com.nakao.pos.util.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.nakao.pos.util.sql.SupplierSQL.*;

/**
 * @author Naoki Nakao on 7/14/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class SupplierRepository implements DAO<Supplier, String> {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Supplier> findAll() {
        return jdbc.query(SELECT_SUPPLIERS, new BeanPropertyRowMapper<>(Supplier.class));
    }

    @Override
    public Optional<Supplier> findById(String id) {
        Supplier supplier;
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            supplier = jdbc.queryForObject(SELECT_SUPPLIER, parameters, new BeanPropertyRowMapper<>(Supplier.class));
        }
        catch (EmptyResultDataAccessException e) {
            supplier = null;
        }

        return Optional.ofNullable(supplier);
    }

    @Override
    public Supplier insert(Supplier supplier) {
        supplier.setId(IdentifierGenerator.generateIdentifier(Supplier.ID_PATTERN));
        MapSqlParameterSource parameters = getSqlParameterSource(supplier);

        jdbc.update(INSERT_SUPPLIER, parameters);

        return supplier;
    }

    @Override
    public Supplier update(String id, Supplier supplier) {
        supplier.setId(id);
        MapSqlParameterSource parameters = getSqlParameterSource(supplier);

        jdbc.update(UPDATE_SUPPLIER, parameters);

        return supplier;
    }

    @Override
    public Boolean delete(String id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbc.update(DELETE_SUPPLIER, parameters) == 1;
    }

    public Integer getSupplierCountByRestock(String id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("supplier", id);

        return jdbc.queryForObject(SUPPLIER_COUNT_BY_RESTOCK, parameters, Integer.class);
    }

    private MapSqlParameterSource getSqlParameterSource(Supplier supplier) {
        return new MapSqlParameterSource()
                .addValue("id", supplier.getId())
                .addValue("name", supplier.getName())
                .addValue("address", supplier.getAddress())
                .addValue("contact", supplier.getContact());
    }

}
