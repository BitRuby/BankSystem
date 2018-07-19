package com.onwelo.practice.bts.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onwelo.practice.bts.exceptions.NotFoundException;
import com.onwelo.practice.bts.exceptions.MissingFieldException;
import com.onwelo.practice.bts.exceptions.UniqueFieldException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<String> handleBankAccountNotFound(RuntimeException e) {
        return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MissingFieldException.class,
            UniqueFieldException.class})
    protected ResponseEntity<String> handleUnprocessableEntity(RuntimeException e) {
        return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<String> handleDataIntegrityViolation(Exception e) {
        return new ResponseEntity<>(gson.toJson("account no is already taken"), HttpStatus.NOT_FOUND);
    }
}
