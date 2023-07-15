package com.nakao.pos.service;

import com.nakao.pos.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Naoki Nakao on 7/14/2023
 * @project POS
 */

@Service
@Transactional
public interface ProductService {

    List<Product> getProducts();

    Product getProductById(String id);

    Product createProduct(Product product);

    Product updateProduct(String id, Product product);

    void deleteProduct(String id);

}
