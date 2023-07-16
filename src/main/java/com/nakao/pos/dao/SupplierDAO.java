package com.nakao.pos.dao;

import com.nakao.pos.model.Supplier;
import com.nakao.pos.util.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.nakao.pos.util.sql.SupplierSQL.INSERT_SUPPLIER;
import static com.nakao.pos.util.sql.SupplierSQL.UPDATE_SUPPLIER;

/**
 * @author Naoki Nakao on 7/14/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class SupplierDAO implements DAO<Supplier, String> {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Supplier> findAll() {
        return null;
    }

    @Override
    public Optional<Supplier> findById(String id) {
        return Optional.empty();
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
        return null;
    }

    private MapSqlParameterSource getSqlParameterSource(Supplier supplier) {
        return new MapSqlParameterSource()
                .addValue("id", supplier.getId())
                .addValue("name", supplier.getName())
                .addValue("address", supplier.getAddress())
                .addValue("contact", supplier.getContact());
    }

}
