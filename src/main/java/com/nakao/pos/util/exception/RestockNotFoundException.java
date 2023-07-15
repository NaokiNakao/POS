package com.nakao.pos.util.exception;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public class RestockNotFoundException extends RuntimeException {

    public RestockNotFoundException(String message) {
        super(message);
    }

}
