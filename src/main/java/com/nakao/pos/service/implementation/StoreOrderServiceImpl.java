package com.nakao.pos.service.implementation;

import com.nakao.pos.dao.StoreOrderDAO;
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

    private final StoreOrderDAO dao;

    @Override
    public List<StoreOrder> getOrders() {
        return dao.findAll();
    }

    @Override
    public StoreOrder getOrderById(UUID id) {
        Optional<StoreOrder> order = dao.findById(id);
        if (order.isPresent()) {
            return order.get();
        }
        else {
            throw new StoreOrderNotFoundException("Store order not found");
        }
    }

    @Override
    public StoreOrder createOrder(StoreOrder storeOrder) {
        return dao.insert(storeOrder);
    }

    @Override
    public StoreOrder updateOrder(UUID id, StoreOrder storeOrder) {
        return dao.update(id, storeOrder);
    }

    @Override
    public void deleteOrder(UUID id) {
        Optional<StoreOrder> order = dao.findById(id);

        if (order.isPresent()) {
            if (isStoreOrderInProgress(order.get().getStatus())) {
                dao.delete(id);
            }
            else {
                throw new StoreOrderDeletionException("Unable to delete store order");
            }
        }
        else {
            throw new StoreOrderNotFoundException("Store order not found");
        }
    }

    @Override
    public OrderItem addOrderItem(String productId, UUID orderId) {
        if (isProductAvailable(productId)) {
            if (isStoreOrderInProgress(getOrderById(orderId).getStatus())) {
                OrderItem addedOrderItem = dao.addItem(generateOrderItem(productId, orderId));
                dao.orderPriceUpdateProcedure(orderId);
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

    @Override
    public void removeOrderItem(String productId, UUID orderId) {
        dao.removeItem(productId, orderId);
        dao.orderPriceUpdateProcedure(orderId);
    }

    @Override
    public void orderProcessing(UUID id) {
        dao.processOrder(id);
    }

    private Boolean isStoreOrderInProgress(String storeOrderStatus) {
        boolean valid = false;

        if (storeOrderStatus.equals(StoreOrderStatus.IN_PROGRESS.getStatus())) {
            valid = true;
        }

        return valid;
    }

    private Boolean isProductAvailable(String productId) {
        boolean available = false;

        if (dao.getProductStock(productId) > 0) {
            available = true;
        }

        return available;
    }

    private OrderItem generateOrderItem(String productId, UUID orderId) {
        return OrderItem.builder()
                .product(productId)
                .storeOrder(orderId)
                .build();
    }

}
