package com.edwise.completespring.controllers;

import com.edwise.completespring.exceptions.InvalidRequestException;
import com.edwise.completespring.exceptions.NotFoundException;
import com.edwise.completespring.exceptions.helpers.ErrorInfo;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo entityNotFound(HttpServletRequest req, NotFoundException ex) {
        String errorURL = req.getRequestURL().toString();

        return new ErrorInfo()
                .setUrl(errorURL)
                .setMessage(ex.getMessage());
    }


    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo invalidPostData(HttpServletRequest req, InvalidRequestException ex) {
        String errorURL = req.getRequestURL().toString();

        return new ErrorInfo()
                .setUrl(errorURL)
                .setMessage(ex.getErrors().toString());
    }


}
