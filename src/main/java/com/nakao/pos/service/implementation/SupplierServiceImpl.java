package com.nakao.pos.service.implementation;

import com.nakao.pos.dao.SupplierDAO;
import com.nakao.pos.model.Supplier;
import com.nakao.pos.service.SupplierService;
import com.nakao.pos.util.exception.SupplierDeletionException;
import com.nakao.pos.util.exception.SupplierNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author Naoki Nakao on 7/14/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierDAO dao;

    @Override
    public List<Supplier> getSuppliers() {
        return dao.findAll();
    }

    @Override
    public Supplier getSupplierById(String id) {
        Optional<Supplier> supplier = dao.findById(id);
        if (supplier.isPresent()) {
            return supplier.get();
        }
        else {
            throw new SupplierNotFoundException("Supplier not found");
        }
    }

    @Override
    public Supplier createSupplier(Supplier supplier) {
        return dao.insert(supplier);
    }

    @Override
    public Supplier updateSupplier(String id, Supplier supplier) {
        return dao.update(id, supplier);
    }

    @Override
    public void deleteSupplier(String id) {
        Optional<Supplier> supplier = dao.findById(id);

        if (supplier.isPresent()) {
            if (validSupplierDeletion()) {
                dao.delete(id);
            }
            else {
                throw new SupplierDeletionException("Unable to delete supplier");
            }
        }
        else {
            throw new SupplierNotFoundException("Supplier not found");
        }
    }
    // TODO: Implement validation
    private boolean validSupplierDeletion() {
        return true;
    }

}
