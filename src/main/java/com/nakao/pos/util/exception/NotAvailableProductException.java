package com.nakao.pos.util.exception;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public class NotAvailableProductException extends RuntimeException {

    public NotAvailableProductException(String message) {
        super(message);
    }

}
