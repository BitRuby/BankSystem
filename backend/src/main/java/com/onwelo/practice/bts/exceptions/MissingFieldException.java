package com.onwelo.practice.bts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class MissingFieldException extends RuntimeException {
    public MissingFieldException() {
        super();
    }

    public MissingFieldException(String message) {
        super(message);
    }

    public MissingFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingFieldException(Throwable cause) {
        super(cause);
    }
}
