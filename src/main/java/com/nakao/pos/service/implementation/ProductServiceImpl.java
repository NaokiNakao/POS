package com.nakao.pos.service.implementation;

import com.nakao.pos.dao.ProductDAO;
import com.nakao.pos.model.Product;
import com.nakao.pos.service.ProductService;
import com.nakao.pos.util.exception.ProductDeletionException;
import com.nakao.pos.util.exception.ProductNotFoundException;
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
public class ProductServiceImpl implements ProductService {

    private final ProductDAO dao;

    @Override
    public List<Product> getProducts() {
        return dao.findAll();
    }

    @Override
    public Product getProductById(String id) {
        Optional<Product> product = dao.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public Product createProduct(Product product) {
        return dao.insert(product);
    }

    @Override
    public Product updateProduct(String id, Product product) {
        return dao.update(id, product);
    }

    @Override
    public void deleteProduct(String id) {
        if (dao.findById(id).isPresent()) {
            if (validProductDeletion()) {
                dao.delete(id);
            }
            else {
                throw new ProductDeletionException("Unable to delete product");
            }
        }
        else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public void productReplenishment(String id, Integer quantity) {
        dao.addStockQuantity(id, quantity);
    }

    private boolean validProductDeletion() {
        return true;
    }

}
