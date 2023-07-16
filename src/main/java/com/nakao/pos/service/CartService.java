package com.nakao.pos.service;

import com.nakao.pos.model.Cart;
import com.nakao.pos.model.CartItem;
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
public interface CartService {

    List<Cart> getCarts();

    Cart getCartById(UUID id);

    Cart createCart(Cart cart);

    Cart updateCart(UUID id, Cart cart);

    void deleteCart(UUID id);

    CartItem addToCart(String productId, UUID cartId);

    void removeCartItem(String productId, UUID cartId);

    // TODO: cartProcessing method

}
