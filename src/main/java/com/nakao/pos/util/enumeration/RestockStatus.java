package com.nakao.pos.util.enumeration;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */
public enum RestockStatus {

    PENDING("PENDING"),
    PROCESSED("PROCESSED"),
    CANCELLED("CANCELLED");

    private final String status;

    RestockStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}

