package org.calisto.hotel.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public interface ErrorHandler<T extends RuntimeException> {

    ResponseEntity<ErrorDetails> handleAlreadyExistsException(T exception, WebRequest webRequest);
}
