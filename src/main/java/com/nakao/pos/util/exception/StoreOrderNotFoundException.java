package com.nakao.pos.util.exception;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public class StoreOrderNotFoundException extends RuntimeException {

    public StoreOrderNotFoundException(String message) {
        super(message);
    }

}
