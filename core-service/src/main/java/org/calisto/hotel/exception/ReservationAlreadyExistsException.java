package org.calisto.hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ReservationAlreadyExistsException extends RuntimeException {
    private String message;

    public ReservationAlreadyExistsException(String message) {
        super(message);
    }
}
