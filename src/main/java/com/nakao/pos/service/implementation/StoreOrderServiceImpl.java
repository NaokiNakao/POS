package com.nakao.pos.service.implementation;

import com.nakao.pos.repository.StoreOrderRepository;
import com.nakao.pos.model.StoreOrder;
import com.nakao.pos.model.OrderItem;
import com.nakao.pos.service.StoreOrderService;
import com.nakao.pos.util.enumeration.StoreOrderStatus;
import com.nakao.pos.util.exception.AlreadyProcessedException;
import com.nakao.pos.util.exception.StoreOrderDeletionException;
import com.nakao.pos.util.exception.StoreOrderNotFoundException;
import com.nakao.pos.util.exception.NotAvailableProductException;
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
public class StoreOrderServiceImpl implements StoreOrderService {

    private final StoreOrderRepository repository;

    @Override
    public List<StoreOrder> getOrders() {
        return repository.findAll();
    }

    @Override
    public StoreOrder getOrderById(UUID id) {
        Optional<StoreOrder> order = repository.findById(id);
        if (order.isPresent()) {
            return order.get();
        }
        else {
            throw new StoreOrderNotFoundException("Store order not found");
        }
    }

    @Override
    public StoreOrder createOrder(StoreOrder storeOrder) {
        return repository.insert(storeOrder);
    }

    @Override
    public StoreOrder updateOrder(UUID id, StoreOrder storeOrder) {
        return repository.update(id, storeOrder);
    }

    @Override
    public void deleteOrder(UUID id) {
        Optional<StoreOrder> order = repository.findById(id);

        if (order.isPresent()) {
            if (isStoreOrderInProgress(order.get().getStatus())) {
                repository.delete(id);
            }
            else {
                throw new StoreOrderDeletionException("Unable to delete store order");
            }
        }
        else {
            throw new StoreOrderNotFoundException("Store order not found");
        }
    }

    /**
     * Adds an order item to the specified order.
     *
     * @param productId The ID of the product to add.
     * @param orderId   The ID of the order to which the item will be added.
     * @return The added order item.
     * @throws AlreadyProcessedException    If the order has already been processed and no more items can be added.
     * @throws NotAvailableProductException If the product is not available.
     */
    @Override
    public OrderItem addOrderItem(String productId, UUID orderId) {
        if (isProductAvailable(productId)) {
            if (isStoreOrderInProgress(getOrderById(orderId).getStatus())) {
                // Generate the order item and add it to the repository
                OrderItem addedOrderItem = repository.addItem(generateOrderItem(productId, orderId));
                repository.orderPriceUpdateProcedure(orderId);
                return addedOrderItem;
            }
            else {
                throw new AlreadyProcessedException("Error: The order is already processed. Not longer able to add items");
            }
        }
        else {
            throw new NotAvailableProductException("Product not available");
        }
    }

    /**
     * Removes an order item from the specified order.
     *
     * @param productId The ID of the product to remove.
     * @param orderId   The ID of the order from which the item will be removed.
     * @throws NotAvailableProductException If the product is not available.
     */
    @Override
    public void removeOrderItem(String productId, UUID orderId) {
        repository.removeItem(productId, orderId);
        repository.orderPriceUpdateProcedure(orderId);
    }

    /**
     * Processes an order with the specified ID.
     *
     * @param id The ID of the order to be processed.
     */
    @Override
    public void orderProcessing(UUID id) {
        repository.processOrder(id);
    }

    private Boolean isStoreOrderInProgress(String storeOrderStatus) {
        boolean isInProgress = false;

        if (storeOrderStatus.equals(StoreOrderStatus.IN_PROGRESS.getStatus())) {
            isInProgress = true;
        }

        return isInProgress;
    }

    private Boolean isProductAvailable(String productId) {
        return repository.getProductStock(productId) > 0;
    }

    private OrderItem generateOrderItem(String productId, UUID orderId) {
        return OrderItem.builder()
                .product(productId)
                .storeOrder(orderId)
                .build();
    }

}
