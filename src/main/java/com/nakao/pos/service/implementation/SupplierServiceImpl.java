package com.nakao.pos.service.implementation;

import com.nakao.pos.repository.SupplierRepository;
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

    private final SupplierRepository repository;

    @Override
    public List<Supplier> getSuppliers() {
        return repository.findAll();
    }

    @Override
    public Supplier getSupplierById(String id) {
        Optional<Supplier> supplier = repository.findById(id);
        if (supplier.isPresent()) {
            return supplier.get();
        }
        else {
            throw new SupplierNotFoundException("Supplier not found");
        }
    }

    @Override
    public Supplier createSupplier(Supplier supplier) {
        return repository.insert(supplier);
    }

    @Override
    public Supplier updateSupplier(String id, Supplier supplier) {
        return repository.update(id, supplier);
    }

    @Override
    public void deleteSupplier(String id) {
        Optional<Supplier> supplier = repository.findById(id);

        if (supplier.isPresent()) {
            if (validSupplierDeletion(id)) {
                repository.delete(id);
            }
            else {
                throw new SupplierDeletionException("Unable to delete supplier");
            }
        }
        else {
            throw new SupplierNotFoundException("Supplier not found");
        }
    }

    private boolean validSupplierDeletion(String id) {
        return repository.getSupplierCountByRestock(id) == 0;
    }

}
