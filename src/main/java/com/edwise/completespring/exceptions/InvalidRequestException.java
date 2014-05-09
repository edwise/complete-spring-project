package com.edwise.completespring.exceptions;

import org.springframework.validation.BindingResult;

/**
 * Created by user EAnton on 07/04/2014.
 */
public class InvalidRequestException extends RuntimeException {

    private BindingResult errors;

    public InvalidRequestException(BindingResult errors) {
        super("Request invalida");
        this.errors = errors;
    }

    public BindingResult getErrors() {
        return errors;
    }
}