package com.nakao.pos.util.exception;

/**
 * @author Naoki Nakao on 7/14/2023
 * @project POS
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

}
