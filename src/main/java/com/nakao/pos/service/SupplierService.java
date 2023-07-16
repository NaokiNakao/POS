package com.nakao.pos.service;

import com.nakao.pos.model.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Naoki Nakao on 7/14/2023
 * @project POS
 */

@Service
@Transactional
public interface SupplierService {

    List<Supplier> getSuppliers();

    Supplier getSupplierById(String id);

    Supplier createSupplier(Supplier supplier);

    Supplier updateSupplier(String id, Supplier supplier);

    void deleteSupplier(String id);

}
