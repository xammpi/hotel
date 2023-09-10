package org.calisto.hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GuestAlreadyExistException extends RuntimeException {
    private String message;

    public GuestAlreadyExistException(String message) {
        super(message);
    }
}
