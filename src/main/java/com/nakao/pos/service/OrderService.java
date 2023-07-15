package com.nakao.pos.service;

import com.nakao.pos.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@Service
@Transactional
public interface OrderService {

    List<Order> getOrders();

    Order getOrderById(UUID id);

    Order createOrder(Order order);

    Order updateOrder(UUID id, Order order);

    void deleteOrder(UUID id);

}
