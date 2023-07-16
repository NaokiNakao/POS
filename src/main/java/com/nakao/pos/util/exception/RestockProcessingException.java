package com.nakao.pos.util.exception;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public class RestockProcessingException extends RuntimeException {

    public RestockProcessingException(String message) {
        super(message);
    }

}
