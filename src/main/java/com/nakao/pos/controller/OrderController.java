package com.nakao.pos.controller;

import com.nakao.pos.model.Order;
import com.nakao.pos.service.OrderService;
import com.nakao.pos.util.exception.OrderDeletionException;
import com.nakao.pos.util.exception.OrderNotFoundException;
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
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable UUID id) {
        try {
            Order order = orderService.getOrderById(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable UUID id, @Valid @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID id) {
        try {
            orderService.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (OrderDeletionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
