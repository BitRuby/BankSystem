package com.onwelo.practice.bts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class UniqueFieldException extends RuntimeException {
    public UniqueFieldException() {
        super();
    }

    public UniqueFieldException(String message) {
        super(message);
    }

    public UniqueFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueFieldException(Throwable cause) {
        super(cause);
    }
}
