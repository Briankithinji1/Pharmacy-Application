package com.brytech.prescription_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class FileUploadFailedException extends RuntimeException {
    
    public FileUploadFailedException(String message) {
        super(message);
    }
}
