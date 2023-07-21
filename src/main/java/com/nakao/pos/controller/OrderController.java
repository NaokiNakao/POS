package com.nakao.pos.controller;

import com.nakao.pos.model.Order;
import com.nakao.pos.model.OrderItem;
import com.nakao.pos.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Naoki Nakao on 7/21/2023
 * @project POS
 */

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size) {
        List<Order> orders = orderService.getOrders(page, size);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable String id) {
        Order order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody @Valid Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable String id, @RequestBody @Valid Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<OrderItem> addItem(@PathVariable String id, @RequestParam("product_sku") String productSku) {
        OrderItem orderItem = orderService.addItem(productSku, id);
        return new ResponseEntity<>(orderItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/items")
    public ResponseEntity<Object> removeItem(@PathVariable String id, @RequestParam("product_sku") String productSku) {
        orderService.removeItem(id, productSku);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/process")
    public ResponseEntity<Object> processOrder(@PathVariable String id) {
        orderService.processOrder(id);
        return new ResponseEntity<>("Order processed", HttpStatus.OK);
    }
}

