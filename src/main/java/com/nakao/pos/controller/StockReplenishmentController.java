package com.nakao.pos.controller;

import com.nakao.pos.model.StockReplenishment;
import com.nakao.pos.service.StockReplenishmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Naoki Nakao on 7/19/2023
 * @project POS
 */

@RestController
@RequestMapping("/api/v1/stock-replenishments")
@RequiredArgsConstructor
public class StockReplenishmentController {

    private final StockReplenishmentService stockReplenishmentService;

    @GetMapping
    public ResponseEntity<List<StockReplenishment>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        List<StockReplenishment> stockReplenishments = stockReplenishmentService.getStockReplenishments(page, size);
        return new ResponseEntity<>(stockReplenishments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockReplenishment> getById(@PathVariable String id) {
        StockReplenishment stockReplenishment = stockReplenishmentService.getStockReplenishmentById(id);
        return new ResponseEntity<>(stockReplenishment, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StockReplenishment> create(@RequestBody @Valid StockReplenishment stockReplenishment) {
        StockReplenishment createdStockReplenishment = stockReplenishmentService.createStockReplenishment(stockReplenishment);
        return new ResponseEntity<>(createdStockReplenishment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockReplenishment> update(@PathVariable String id, @RequestBody @Valid StockReplenishment stockReplenishment) {
        StockReplenishment updatedStockReplenishment = stockReplenishmentService.updateStockReplenishment(id, stockReplenishment);
        return new ResponseEntity<>(updatedStockReplenishment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        stockReplenishmentService.deleteStockReplenishment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/process")
    public ResponseEntity<Object> process(@PathVariable String id) {
        stockReplenishmentService.replenishmentProcessing(id);
        return new ResponseEntity<>("Stock Replenishment processed", HttpStatus.OK);
    }

}
