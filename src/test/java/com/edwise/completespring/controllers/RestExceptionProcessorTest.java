package com.edwise.completespring.controllers;

import com.edwise.completespring.exceptions.InvalidRequestException;
import com.edwise.completespring.exceptions.NotFoundException;
import com.edwise.completespring.exceptions.helpers.ErrorInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RestExceptionProcessorTest {

    private RestExceptionProcessor restExceptionProcessor;

    private MockHttpServletRequest request;

    @Mock
    BindingResult errors;

    @Before
    public void setUp() {
        this.request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));
        MockitoAnnotations.initMocks(this);
        this.restExceptionProcessor = new RestExceptionProcessor();
    }

    @Test
    public void testEntityNotFound() {
        NotFoundException exception = new NotFoundException("No existe la entidad");
        ErrorInfo errorInfo = restExceptionProcessor.entityNotFound(request, exception);
        assertNotNull("No puede ser nulo", errorInfo);
        assertEquals("Deben ser mensajes iguales", errorInfo.getMessage(), exception.getMessage());
    }

    @Test
    public void testInvalidPostData() {
        InvalidRequestException exception = new InvalidRequestException(errors);
        ErrorInfo errorInfo = restExceptionProcessor.invalidPostData(request, exception);
        assertNotNull("No puede ser nulo", errorInfo);
        assertEquals("Deben ser mensajes iguales", errorInfo.getMessage(), exception.getErrors().toString());
    }
}