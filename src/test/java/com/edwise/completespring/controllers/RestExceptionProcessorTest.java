package com.edwise.completespring.controllers;

import com.edwise.completespring.exceptions.InvalidRequestException;
import com.edwise.completespring.exceptions.NotFoundException;
import com.edwise.completespring.exceptions.helpers.ErrorInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestExceptionProcessorTest {
    private static final int ONE_ITEM = 1;
    private static final int TWO_ITEMS = 2;
    private static final String FIELD_ERROR_OBJECT_TEST1 = "Book";
    private static final String FIELD_ERROR_FIELD_TEST1 = "title";
    private static final String FIELD_ERROR_FIELD_TEST2 = "isbn";
    private static final String FIELD_ERROR_MESSAGE_TEST1 = "No puede ser nulo";
    private static final String FIELD_ERROR_MESSAGE_TEST2 = "No puede ser vacio";
    private static final String EXCEPTION_MESSAGE_TEST1 = "No existe la entidad";

    private RestExceptionProcessor restExceptionProcessor;

    private MockHttpServletRequest request;

    @Mock
    private BindingResult errors;

    @Before
    public void setUp() {
        this.request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));
        this.restExceptionProcessor = new RestExceptionProcessor();
    }

    @Test
    public void testEntityNotFound() {
        NotFoundException exception = new NotFoundException(EXCEPTION_MESSAGE_TEST1);

        ErrorInfo errorInfo = restExceptionProcessor.entityNotFound(request, exception);

        assertThat(errorInfo).isNotNull();
        assertThat(errorInfo.getUrl()).isEqualTo(request.getRequestURL().toString());
        assertThat(errorInfo.getErrors()).hasSize(ONE_ITEM);
    }

    @Test
    public void testInvalidPostData() {
        when(errors.getFieldErrors()).thenReturn(createMockListFieldErrors());
        InvalidRequestException exception = new InvalidRequestException(errors);

        ErrorInfo errorInfo = restExceptionProcessor.invalidPostData(request, exception);

        assertThat(errorInfo).isNotNull();
        assertThat(errorInfo.getUrl()).isEqualTo(request.getRequestURL().toString());
        assertThat(errorInfo.getErrors()).hasSize(TWO_ITEMS);
    }

    private List<FieldError> createMockListFieldErrors() {
        return Arrays.asList(
                new FieldError(FIELD_ERROR_OBJECT_TEST1, FIELD_ERROR_FIELD_TEST1, FIELD_ERROR_MESSAGE_TEST1),
                new FieldError(FIELD_ERROR_OBJECT_TEST1, FIELD_ERROR_FIELD_TEST2, FIELD_ERROR_MESSAGE_TEST2));
    }

}