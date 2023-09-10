package org.calisto.hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RoomAlreadyExistsException extends RuntimeException {
    private String message;

    public RoomAlreadyExistsException(String message) {
        super(message);
    }
}
