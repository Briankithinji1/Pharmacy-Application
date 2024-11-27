package com.brytech.prescription_service.exceptions;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleException(
            ResourceNotFoundException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(), 
                e.getMessage(), 
                HttpStatus.NOT_FOUND.value(), 
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleException(
            DuplicateResourceException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ApiError> handleException(
            RequestValidationException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApiError> handleException(
            TokenExpiredException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnsupportedFileException.class)
    public ResponseEntity<ApiError> handleException(
            UnsupportedFileException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(), 
                e.getMessage(), 
                HttpStatus.BAD_REQUEST.value(), 
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileUploadFailedException.class)
    public ResponseEntity<ApiError> handelException(
            FileUploadFailedException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(), 
                e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
