package com.nakao.pos.util.exception;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public class OrderDeletionException extends RuntimeException {

    public OrderDeletionException(String message) {
        super(message);
    }

}
