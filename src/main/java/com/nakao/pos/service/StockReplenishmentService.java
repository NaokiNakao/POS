package com.nakao.pos.service;

import com.nakao.pos.dao.StockReplenishmentDAO;
import com.nakao.pos.enumeration.StockReplenishmentStatus;
import com.nakao.pos.exception.StockReplenishmentDeletionException;
import com.nakao.pos.exception.StockReplenishmentNotFoundException;
import com.nakao.pos.exception.StockReplenishmentProcessingException;
import com.nakao.pos.model.StockReplenishment;
import com.nakao.pos.repository.ProductRepository;
import com.nakao.pos.repository.StockReplenishmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author Naoki Nakao on 7/19/2023
 * @project POS
 */

@Service
@Transactional
@RequiredArgsConstructor
public class StockReplenishmentService {

    private final StockReplenishmentRepository stockReplenishmentRepository;
    private final StockReplenishmentDAO stockReplenishmentDAO;
    private final ProductRepository productRepository;

    public List<StockReplenishment> getStockReplenishments(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<StockReplenishment> page = stockReplenishmentRepository.findAll(pageable);
        return page.getContent();
    }

    public StockReplenishment getStockReplenishmentById(String id) {
        return stockReplenishmentRepository.findById(id)
                .orElseThrow(() -> new StockReplenishmentNotFoundException("Stock Replenishment not found with ID: " + id));
    }

    public StockReplenishment createStockReplenishment(StockReplenishment stockReplenishment) {
        stockReplenishment.setId(UUID.randomUUID().toString());
        stockReplenishmentDAO.insert(stockReplenishment);
        return stockReplenishment;
    }

    public StockReplenishment updateStockReplenishment(String id, StockReplenishment stockReplenishment) {
        StockReplenishment updatedStockReplenishment = getStockReplenishmentById(id);
        BeanUtils.copyProperties(stockReplenishment, updatedStockReplenishment);
        updatedStockReplenishment.setId(id);
        return stockReplenishmentRepository.save(updatedStockReplenishment);
    }

    public void deleteStockReplenishment(String id) {
        StockReplenishment stockReplenishment = getStockReplenishmentById(id);

        if (stockReplenishment.getStatus().equals(StockReplenishmentStatus.PENDING.getValue())) {
            stockReplenishmentRepository.deleteById(id);
        }
        else {
            throw new StockReplenishmentDeletionException("Unable to delete Stock Replenishment with ID: " + id);
        }
    }

    public void replenishmentProcessing(String id) {
        StockReplenishment stockReplenishment = getStockReplenishmentById(id);

        if (stockReplenishment.getStatus().equals(StockReplenishmentStatus.PENDING.getValue())) {
            productRepository.updateProductStock(stockReplenishment.getProductSku(),
                    stockReplenishment.getProductQuantity());
            stockReplenishmentRepository.updateStockReplenishmentStatus(stockReplenishment.getId(),
                    StockReplenishmentStatus.DELIVERED.getValue());
        }
        else {
            throw new StockReplenishmentProcessingException("Unable to process Stock Replenishment");
        }
    }

}
