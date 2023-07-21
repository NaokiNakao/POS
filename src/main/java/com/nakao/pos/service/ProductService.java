package com.nakao.pos.service;

import com.nakao.pos.dao.ProductDAO;
import com.nakao.pos.exception.ProductDeletionException;
import com.nakao.pos.exception.ProductNotFoundException;
import com.nakao.pos.exception.UniqueIdentifierGenerationException;
import com.nakao.pos.model.Product;
import com.nakao.pos.repository.ProductRepository;
import com.nakao.pos.repository.StockReplenishmentRepository;
import com.nakao.pos.util.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Naoki Nakao on 7/18/2023
 * @project POS
 */

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDAO productDAO;
    private final StockReplenishmentRepository stockReplenishmentRepository;

    public List<Product> getProducts(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = productRepository.findAll(pageable);
        return page.getContent();
    }

    public Product getProductBySku(String sku) {
        return productRepository.findById(sku)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with SKU: " + sku));
    }

    public Product createProduct(Product product) {
        product.setSku(IdentifierGenerator.generateIdentifier(Product.SKU_PATTERN));

        if (!productRepository.existsById(product.getSku())) {
            productDAO.insert(product);
            return product;
        }
        else {
            throw new UniqueIdentifierGenerationException("Error generating unique identifier for Product");
        }
    }

    public Product updateProduct(String sku, Product product) {
        Product updatedProduct = getProductBySku(sku);
        BeanUtils.copyProperties(product, updatedProduct);
        updatedProduct.setSku(sku);
        return productRepository.save(updatedProduct);
    }

    public void deleteProduct(String sku) {
        if (productRepository.existsById(sku)) {
            if (isValidProductDeletion(sku)) {
                productRepository.deleteById(sku);
            }
            else {
                throw new ProductDeletionException("Unable to delete Product with SKU: " + sku);
            }
        }
        else {
            throw new ProductNotFoundException("Product not found with SKU: " + sku);
        }
    }

    private Boolean isValidProductDeletion(String sku) {
        return stockReplenishmentRepository.countStockReplenishmentByProductSku(sku) == 0;
    }

}

