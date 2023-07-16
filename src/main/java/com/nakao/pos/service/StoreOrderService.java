package com.nakao.pos.service;

import com.nakao.pos.model.StoreOrder;
import com.nakao.pos.model.OrderItem;
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
public interface StoreOrderService {

    List<StoreOrder> getOrders();

    StoreOrder getOrderById(UUID id);

    StoreOrder createOrder(StoreOrder storeOrder);

    StoreOrder updateOrder(UUID id, StoreOrder storeOrder);

    void deleteOrder(UUID id);

    OrderItem addOrderItem(String productId, UUID orderId);

    void removeOrderItem(String productId, UUID orderId);

    void orderProcessing(UUID id);

}
