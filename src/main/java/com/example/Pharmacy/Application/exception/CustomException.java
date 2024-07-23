package main.java.com.example.Pharmacy.Application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomException extends RuntimeException{

    public CustomException(String message) {
        super(message);
    }
}
