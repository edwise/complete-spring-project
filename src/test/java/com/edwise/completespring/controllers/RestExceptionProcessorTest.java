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
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

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
        assertEquals("Deben ser urls iguales", errorInfo.getUrl(), request.getRequestURL().toString());
        assertEquals("Deben ser mensajes iguales", errorInfo.getErrors().size(), 1);
    }

    @Test
    public void testInvalidPostData() {
        when(errors.getFieldErrors()).thenReturn(Arrays.asList(
                new FieldError("field1", "field1", "No puede ser nulo"),
                new FieldError("field2", "field2", "No puede ser vacio")));
        InvalidRequestException exception = new InvalidRequestException(errors);
        ErrorInfo errorInfo = restExceptionProcessor.invalidPostData(request, exception);
        assertNotNull("No puede ser nulo", errorInfo);
        assertEquals("Deben ser urls iguales", errorInfo.getUrl(), request.getRequestURL().toString());
        assertEquals("Deben ser mensajes iguales", errorInfo.getErrors().size(), 2);
    }

}