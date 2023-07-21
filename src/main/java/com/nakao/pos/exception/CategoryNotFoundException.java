package com.nakao.pos.exception;

import com.nakao.pos.exception.common.ApiRequestException;
import org.springframework.http.HttpStatus;

/**
 * @author Naoki Nakao on 7/18/2023
 * @project POS
 */
public class CategoryNotFoundException extends ApiRequestException {

    public CategoryNotFoundException(String message) {
        super(message);
        setHttpStatus(HttpStatus.NOT_FOUND);
    }

}
