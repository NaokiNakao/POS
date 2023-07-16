package com.nakao.pos.util.exception;

/**
 * @author Naoki Nakao on 7/16/2023
 * @project POS
 */
public class AlreadyProcessedException extends RuntimeException {

    public AlreadyProcessedException(String message) {
        super(message);
    }

}
