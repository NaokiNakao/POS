package com.nakao.pos.repository;

import com.nakao.pos.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
}
