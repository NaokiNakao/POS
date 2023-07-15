package com.nakao.pos.controller;

import com.nakao.pos.model.Cart;
import com.nakao.pos.model.CartItem;
import com.nakao.pos.service.CartService;
import com.nakao.pos.util.exception.CartDeletionException;
import com.nakao.pos.util.exception.CartNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@RestController
@RequestMapping(path = "/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<Cart>> getCarts() {
        List<Cart> carts = cartService.getCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCart(@PathVariable UUID id) {
        try {
            Cart cart = cartService.getCartById(id);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (CartNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@Valid @RequestBody Cart cart) {
        Cart createdCart = cartService.createCart(cart);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable UUID id, @Valid @RequestBody Cart cart) {
        Cart updatedCart = cartService.updateCart(id, cart);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable UUID id) {
        try {
            cartService.deleteCart(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (CartNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (CartDeletionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/add_item")
    public ResponseEntity<CartItem> addCartItem(@PathVariable UUID id, @RequestParam String product) {
        CartItem cartItem = cartService.addToCart(product, id);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/remove_item")
    public ResponseEntity<String> removeCartItem(@PathVariable UUID id, @RequestParam String product) {
        cartService.removeCartItem(product, id);
        return new ResponseEntity<>("Item removed", HttpStatus.NO_CONTENT);
    }

}
