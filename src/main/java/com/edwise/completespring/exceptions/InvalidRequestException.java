package com.edwise.completespring.exceptions;

import com.edwise.completespring.exceptions.helpers.ErrorInfo;
import lombok.Getter;
import org.springframework.validation.BindingResult;

public class InvalidRequestException extends RuntimeException {

    @Getter
    private final ErrorInfo errors;

    public InvalidRequestException(BindingResult bindingResultErrors) {
        super(bindingResultErrors.toString());
        this.errors = ErrorInfo.generateErrorInfoFromBindingResult(bindingResultErrors);
    }

}