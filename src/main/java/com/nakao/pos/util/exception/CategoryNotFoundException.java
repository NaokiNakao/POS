package com.nakao.pos.util.exception;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }

}
