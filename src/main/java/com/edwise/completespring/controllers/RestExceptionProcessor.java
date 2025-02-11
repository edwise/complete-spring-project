package com.edwise.completespring.controllers;

import com.edwise.completespring.exceptions.InvalidRequestException;
import com.edwise.completespring.exceptions.NotFoundException;
import com.edwise.completespring.exceptions.helpers.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
@Slf4j
public class RestExceptionProcessor {
    private static final String FIELD_ID = "id";

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo entityNotFound(WebRequest req, NotFoundException ex) {
        String errorDescription = req.getDescription(false);

        log.warn("Entity not found: {}", errorDescription);
        return new ErrorInfo()
                .setUrl(errorDescription)
                .addError(FIELD_ID, ex.getMessage());
    }


    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo invalidPostData(WebRequest req, InvalidRequestException ex) {
        ErrorInfo errors = ex.getErrors();
        errors.setUrl(req.getDescription(false));

        log.warn("Invalid request: {}", errors);
        return errors;
    }

}
