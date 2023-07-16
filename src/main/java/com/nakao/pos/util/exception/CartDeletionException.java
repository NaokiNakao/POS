package com.nakao.pos.util.exception;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public class CartDeletionException extends RuntimeException {

    public CartDeletionException(String message) {
        super(message);
    }

}
