package com.nakao.pos.util.exception;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }

}
