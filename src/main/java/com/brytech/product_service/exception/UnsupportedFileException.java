package com.brytech.product_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UnsupportedFileException extends RuntimeException {

    public UnsupportedFileException(String message) {
        super(message);
    }
}
