package com.nakao.pos.exception;

import com.nakao.pos.exception.common.ApiRequestException;
import org.springframework.http.HttpStatus;

/**
 * @author Naoki Nakao on 7/22/2023
 * @project POS
 */
public class CustomerDeletionException extends ApiRequestException {

    public CustomerDeletionException(String message) {
        super(message);
        setHttpStatus(HttpStatus.CONFLICT);
    }

}
