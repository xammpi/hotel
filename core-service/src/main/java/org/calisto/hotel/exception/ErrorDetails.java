package org.calisto.hotel.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorDetails {

    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private String path;
    private String errorCode;

    public ErrorDetails(String message, String path, String errorCode) {
        this.message = message;
        this.path = path;
        this.errorCode = errorCode;
    }
}
