package com.nakao.pos.service.implementation;

import com.nakao.pos.repository.ProductRepository;
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

    private final ProductRepository repository;

    @Override
    public List<Product> getProducts() {
        return repository.findAll();
    }

    @Override
    public Product getProductById(String id) {
        Optional<Product> product = repository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public Product createProduct(Product product) {
        return repository.insert(product);
    }

    @Override
    public Product updateProduct(String id, Product product) {
        return repository.update(id, product);
    }

    @Override
    public void deleteProduct(String id) {
        if (repository.findById(id).isPresent()) {
            if (validProductDeletion(id)) {
                repository.delete(id);
            }
            else {
                throw new ProductDeletionException("Unable to delete product");
            }
        }
        else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    private Boolean validProductDeletion(String id) {
        return repository.getProductCountByItemsExistence(id) == 0;
    }

}
