package com.nakao.pos.util.enumeration;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public enum RestockStatus {

    IN_PROGRESS("IN_PROGRESS"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    private final String status;

    RestockStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}

