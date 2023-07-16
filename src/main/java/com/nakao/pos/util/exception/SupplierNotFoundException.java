package com.nakao.pos.util.exception;

/**
 * @author Naoki Nakao on 7/14/2023
 * @project POS
 */
public class SupplierNotFoundException extends RuntimeException {

    public SupplierNotFoundException(String message) {
        super(message);
    }

}
