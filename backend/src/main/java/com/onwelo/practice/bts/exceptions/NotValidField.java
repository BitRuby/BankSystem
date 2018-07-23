package com.onwelo.practice.bts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotValidField extends RuntimeException {
    public NotValidField() {
        super();
    }

    public NotValidField(String message) {
        super(message);
    }

    public NotValidField(Throwable cause) {
        super(cause);
    }

    public NotValidField(String message, Throwable cause) {
        super(message, cause);
    }
}