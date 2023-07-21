package com.nakao.pos.exception;

import com.nakao.pos.exception.common.ApiRequestException;
import org.springframework.http.HttpStatus;

/**
 * @author Naoki Nakao on 7/19/2023
 * @project POS
 */
public class SupplierNotFoundException extends ApiRequestException {

    public SupplierNotFoundException(String message) {
        super(message);
        setHttpStatus(HttpStatus.NOT_FOUND);
    }

}
