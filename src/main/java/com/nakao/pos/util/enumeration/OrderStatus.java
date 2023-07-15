package com.nakao.pos.util.enumeration;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public enum OrderStatus {

    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
