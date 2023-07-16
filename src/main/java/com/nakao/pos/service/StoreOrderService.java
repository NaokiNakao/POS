package com.nakao.pos.service;

import com.nakao.pos.model.StoreOrder;
import com.nakao.pos.model.OrderItem;
import com.nakao.pos.util.exception.AlreadyProcessedException;
import com.nakao.pos.util.exception.NotAvailableProductException;
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

    /**
     * Adds an order item to the specified order.
     *
     * @param productId The ID of the product to add.
     * @param orderId   The ID of the order to which the item will be added.
     * @return The added order item.
     * @throws AlreadyProcessedException    If the order has already been processed and no more items can be added.
     * @throws NotAvailableProductException If the product is not available.
     */
    OrderItem addOrderItem(String productId, UUID orderId);

    /**
     * Removes an order item from the specified order.
     *
     * @param productId The ID of the product to remove.
     * @param orderId   The ID of the order from which the item will be removed.
     * @throws NotAvailableProductException If the product is not available.
     */
    void removeOrderItem(String productId, UUID orderId);

    /**
     * Processes an order with the specified ID.
     *
     * @param id The ID of the order to be processed.
     */
    void orderProcessing(UUID id);

}
