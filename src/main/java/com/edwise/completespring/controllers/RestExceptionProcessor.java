package com.edwise.completespring.controllers;

import com.edwise.completespring.exceptions.InvalidRequestException;
import com.edwise.completespring.exceptions.NotFoundException;
import com.edwise.completespring.exceptions.helpers.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by EAnton on 09/05/2014.
 */
@ControllerAdvice
public class RestExceptionProcessor {
    private static final String FIELD_ID = "id";

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo entityNotFound(HttpServletRequest req, NotFoundException ex) {
        String errorURL = req.getRequestURL().toString();

        return new ErrorInfo()
                .setUrl(errorURL)
                .addError(FIELD_ID, ex.getMessage());
    }


    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo invalidPostData(HttpServletRequest req, InvalidRequestException ex) {
        String errorURL = req.getRequestURL().toString();
        return generateErrorInfoFromBindingResult(ex.getErrors()).setUrl(errorURL);
    }

    private ErrorInfo generateErrorInfoFromBindingResult(BindingResult errors) {
        ErrorInfo errorInfo = new ErrorInfo();
        for (FieldError fieldError : errors.getFieldErrors()) {
            errorInfo.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return errorInfo;
    }
}
