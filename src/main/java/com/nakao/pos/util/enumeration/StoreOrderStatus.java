package com.nakao.pos.util.enumeration;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public enum StoreOrderStatus {

    IN_PROGRESS("IN_PROGRESS"),
    PROCESSED("PROCESSED");

    private final String status;

    StoreOrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
