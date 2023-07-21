package com.nakao.pos.service;

import com.nakao.pos.dao.OrderDAO;
import com.nakao.pos.enumeration.OrderStatus;
import com.nakao.pos.exception.*;
import com.nakao.pos.model.Order;
import com.nakao.pos.model.OrderItem;
import com.nakao.pos.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Naoki Nakao on 7/20/2023
 * @project POS
 */

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDAO orderDAO;

    public List<Order> getOrders(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Order> page = orderRepository.findAll(pageable);
        return page.getContent();
    }

    public Order getOrderById(String  id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
    }

    public Order createOrder(Order order) {
        order.setId(UUID.randomUUID().toString());

        if (!orderRepository.existsById(order.getId())) {
            orderDAO.insert(order);
            return order;
        } else {
            throw new UniqueIdentifierGenerationException("Error generating unique identifier for Order");
        }
    }

    public Order updateOrder(String id, Order order) {
        Order updatedOrder = getOrderById(id);
        BeanUtils.copyProperties(order, updatedOrder);
        updatedOrder.setId(id);
        return orderRepository.save(updatedOrder);
    }

    public void deleteOrder(String  id) {
        Order order = getOrderById(id);

        if (order.getStatus().equals(OrderStatus.IN_PROGRESS.getValue())) {
            orderRepository.deleteById(id);
        } else {
            throw new OrderDeletionException("Unable to delete Order with ID: " + id);
        }
    }

    public OrderItem addItem(String productSku, String orderId) {
        Order order = getOrderById(orderId);
        validateOrderStatus(order);

        OrderItem orderItem = generateOrderItem(productSku, orderId);
        updateOrderWithNewItem(order, orderItem);

        return orderItem;
    }

    public void removeItem(String orderId, String productSku) {
        Order order = getOrderById(orderId);
        validateOrderStatus(order);
        String orderItemId = orderRepository.getFirstOrderItemIdWithProductSku(orderId, productSku);

        if (orderItemId != null) {
            orderDAO.deleteItem(orderItemId);
            updateOrderPrice(order);
        } else {
            throw new OrderItemNotFoundException("Order item with product SKU: " + productSku + " not found in order with ID: " + orderId);
        }
    }

    public void processOrder(String id) {
        Order order = getOrderById(id);
        order.setDate(LocalDate.now());
        order.setStatus(OrderStatus.PROCESSED.getValue());
        orderRepository.save(order);
    }

    private void validateOrderStatus(Order order) {
        if (!order.getStatus().equals(OrderStatus.IN_PROGRESS.getValue())) {
            throw new OrderAlreadyProcessedException("Order with ID: " + order.getId() + " already processed");
        }
    }

    private void updateOrderWithNewItem(Order order, OrderItem orderItem) {
        orderDAO.insertItem(orderItem);
        updateOrderPrice(order);
    }

    private OrderItem generateOrderItem(String productSku, String orderId) {
        return OrderItem.builder()
                .id(UUID.randomUUID().toString())
                .productSku(productSku)
                .orderId(orderId)
                .build();
    }

    private void updateOrderPrice(Order order) {
        BigDecimal net = orderRepository.getOrderItemsNetSum(order.getId());
        BigDecimal tax = net.multiply(BigDecimal.valueOf(0.10));

        order.setNet(net);
        order.setTax(tax);
        order.setTotal(net.add(tax));

        orderRepository.save(order);
    }

}
