package com.nakao.pos.service.implementation;

import com.nakao.pos.dao.OrderDAO;
import com.nakao.pos.model.Order;
import com.nakao.pos.service.OrderService;
import com.nakao.pos.util.enumeration.OrderStatus;
import com.nakao.pos.util.exception.OrderDeletionException;
import com.nakao.pos.util.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDAO dao;

    @Override
    public List<Order> getOrders() {
        return dao.findAll();
    }

    @Override
    public Order getOrderById(UUID id) {
        Optional<Order> order = dao.findById(id);
        if (order.isPresent()) {
            return order.get();
        }
        else {
            throw new OrderNotFoundException("Order not found");
        }
    }

    @Override
    public Order createOrder(Order order) {
        return dao.insert(order);
    }

    @Override
    public Order updateOrder(UUID id, Order order) {
        return dao.update(id, order);
    }

    @Override
    public void deleteOrder(UUID id) {
        Optional<Order> order = dao.findById(id);

        if (order.isPresent()) {
            if (validOrderDeletion(order.get())) {
                dao.delete(id);
            }
            else {
                throw new OrderDeletionException("Unable to delete order");
            }
        }
        else {
            throw new OrderNotFoundException("Order not found");
        }
    }

    private Boolean validOrderDeletion(Order order) {
        boolean valid = false;

        if (order.getStatus().equals(OrderStatus.IN_PROGRESS.getStatus())) {
            valid = true;
        }

        return valid;
    }

}
