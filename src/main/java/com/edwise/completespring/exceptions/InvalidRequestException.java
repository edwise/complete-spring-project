package com.edwise.completespring.exceptions;

import org.springframework.validation.BindingResult;

public class InvalidRequestException extends RuntimeException {

    private final BindingResult errors;

    public InvalidRequestException(BindingResult errors) {
        super(errors.toString());
        this.errors = errors;
    }

    public BindingResult getErrors() {
        return errors;
    }
}