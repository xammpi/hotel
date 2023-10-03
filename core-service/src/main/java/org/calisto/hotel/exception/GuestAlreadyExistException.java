package org.calisto.hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GuestAlreadyExistException extends RuntimeException {
    private String message;

    public GuestAlreadyExistException(String message) {
        super(message);
    }


}
