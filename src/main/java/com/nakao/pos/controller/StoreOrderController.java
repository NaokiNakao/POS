package com.nakao.pos.controller;

import com.nakao.pos.model.StoreOrder;
import com.nakao.pos.model.OrderItem;
import com.nakao.pos.service.StoreOrderService;
import com.nakao.pos.util.exception.StoreOrderDeletionException;
import com.nakao.pos.util.exception.StoreOrderNotFoundException;
import com.nakao.pos.util.exception.NotAvailableProductException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@RestController
@RequestMapping(path = "/api/orders")
@RequiredArgsConstructor
public class StoreOrderController {

    private final StoreOrderService storeOrderService;

    @GetMapping
    public ResponseEntity<List<StoreOrder>> getOrders() {
        List<StoreOrder> storeOrders = storeOrderService.getOrders();
        return ResponseEntity.ok(storeOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable UUID id) {
        try {
            StoreOrder storeOrder = storeOrderService.getOrderById(id);
            return new ResponseEntity<>(storeOrder, HttpStatus.OK);
        } catch (StoreOrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<StoreOrder> createOrder(@Valid @RequestBody StoreOrder storeOrder) {
        StoreOrder createdStoreOrder = storeOrderService.createOrder(storeOrder);
        return new ResponseEntity<>(createdStoreOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreOrder> updateOrder(@PathVariable UUID id, @Valid @RequestBody StoreOrder storeOrder) {
        StoreOrder updatedStoreOrder = storeOrderService.updateOrder(id, storeOrder);
        return new ResponseEntity<>(updatedStoreOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID id) {
        try {
            storeOrderService.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (StoreOrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (StoreOrderDeletionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/add_item")
    public ResponseEntity<?> addOrderItem(@PathVariable UUID id, @RequestParam String product) {
        try {
            OrderItem orderItem = storeOrderService.addOrderItem(product, id);
            return new ResponseEntity<>(orderItem, HttpStatus.CREATED);
        }
        catch (NotAvailableProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}/remove_item")
    public ResponseEntity<String> removeOrderItem(@PathVariable UUID id, @RequestParam String product) {
        storeOrderService.removeOrderItem(product, id);
        return new ResponseEntity<>("Item removed", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/process")
    public ResponseEntity<String> cartProcessing(@PathVariable UUID id) {
        storeOrderService.orderProcessing(id);
        return new ResponseEntity<>("Order processed", HttpStatus.OK);
    }

}
