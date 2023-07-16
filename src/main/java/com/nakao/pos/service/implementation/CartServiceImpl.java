package com.nakao.pos.service.implementation;

import com.nakao.pos.dao.CartDAO;
import com.nakao.pos.model.Cart;
import com.nakao.pos.model.CartItem;
import com.nakao.pos.service.CartService;
import com.nakao.pos.util.enumeration.OrderStatus;
import com.nakao.pos.util.exception.CartDeletionException;
import com.nakao.pos.util.exception.CartNotFoundException;
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
public class CartServiceImpl implements CartService {

    private final CartDAO dao;

    @Override
    public List<Cart> getCarts() {
        return dao.findAll();
    }

    @Override
    public Cart getCartById(UUID id) {
        Optional<Cart> cart = dao.findById(id);
        if (cart.isPresent()) {
            return cart.get();
        }
        else {
            throw new CartNotFoundException("Cart not found");
        }
    }

    @Override
    public Cart createCart(Cart cart) {
        return dao.insert(cart);
    }

    @Override
    public Cart updateCart(UUID id, Cart cart) {
        return dao.update(id, cart);
    }

    @Override
    public void deleteCart(UUID id) {
        Optional<Cart> cart = dao.findById(id);

        if (cart.isPresent()) {
            if (validCartDeletion(cart.get())) {
                dao.delete(id);
            }
            else {
                throw new CartDeletionException("Unable to delete cart");
            }
        }
        else {
            throw new CartNotFoundException("Cart not found");
        }
    }

    @Override
    public CartItem addToCart(String productId, UUID cartId) {
        if (isProductAvailable(productId)) {
            CartItem cartItem = CartItem.builder()
                    .product(productId)
                    .cart(cartId)
                    .build();

            CartItem addedCartItem = dao.addItem(cartItem);
            dao.cartPriceUpdateProcedure(cartId);
            return addedCartItem;
        }
        else {
            throw new NotAvailableProductException("Product not available");
        }
    }

    @Override
    public void removeCartItem(String productId, UUID cartId) {
        dao.removeItem(productId, cartId);
    }

    private Boolean validCartDeletion(Cart cart) {
        boolean valid = false;

        if (cart.getStatus().equals(OrderStatus.IN_PROGRESS.getStatus())) {
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

}
