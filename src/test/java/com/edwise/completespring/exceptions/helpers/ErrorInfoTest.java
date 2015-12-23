package com.edwise.completespring.exceptions.helpers;

import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ErrorInfoTest {
    private static final int TWO_ITEMS = 2;
    private static final int THREE_ITEMS = 3;
    private static final String FIELD_TEST1 = "Field1";
    private static final String FIELD_TEST2 = "Field2";
    private static final String MESSAGE_TEST1 = "MessageText";
    private static final String MESSAGE_TEST2 = "MessageText2";
    private static final String URL_TEST1 = "URL_test";
    private static final String TITLE_FIELD = "title";
    private static final String ISBN_FIELD = "isbn";
    private static final String RELEASE_DATE_FIELD = "releaseDate";
    private static final String IT_CANT_BE_NULL_MSG = "No puede ser nulo";
    private static final String IT_CANT_BE_EMPTY_MSG = "No puede ser vacio";
    private static final String BOOK_OBJECT_NAME = "Book";

    @Test
    public void testAddError() {
        ErrorInfo errorInfo = createErrorInfo(null).addError(FIELD_TEST1, MESSAGE_TEST1).addError(FIELD_TEST2,
                MESSAGE_TEST2);

        assertNotNull(errorInfo.getErrors());
        assertEquals(TWO_ITEMS, errorInfo.getErrors().size());
    }

    @Test
    public void testGenerateErrorInfoFromBindingResult() {
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.getFieldErrors()).thenReturn(createMockListFieldErrors());

        ErrorInfo errorInfo = ErrorInfo.generateErrorInfoFromBindingResult(bindingResultMock);

        assertNotNull(errorInfo);
        assertThat(errorInfo.getErrors(), hasSize(THREE_ITEMS));
        assertThat(errorInfo.getErrors(),
                containsInAnyOrder(
                        new ErrorItem().setField(TITLE_FIELD).setMessage(IT_CANT_BE_NULL_MSG),
                        new ErrorItem().setField(ISBN_FIELD).setMessage(IT_CANT_BE_EMPTY_MSG),
                        new ErrorItem().setField(RELEASE_DATE_FIELD).setMessage(IT_CANT_BE_NULL_MSG)));
        verify(bindingResultMock).getFieldErrors();
    }

    @Test
    public void testGenerateErrorInfoFromBindingResultEmpty() {
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.getFieldErrors()).thenReturn(new ArrayList<>());

        ErrorInfo errorInfo = ErrorInfo.generateErrorInfoFromBindingResult(bindingResultMock);

        assertNotNull(errorInfo);
        assertThat(errorInfo.getErrors(), hasSize(0));
        verify(bindingResultMock).getFieldErrors();
    }

    private List<FieldError> createMockListFieldErrors() {
        return Arrays.asList(
                new FieldError(BOOK_OBJECT_NAME, TITLE_FIELD, IT_CANT_BE_NULL_MSG),
                new FieldError(BOOK_OBJECT_NAME, ISBN_FIELD, IT_CANT_BE_EMPTY_MSG),
                new FieldError(BOOK_OBJECT_NAME, RELEASE_DATE_FIELD, IT_CANT_BE_NULL_MSG));
    }

    @Test
    public void testEquals() {
        ErrorInfo errorInfo1 = createErrorInfo(URL_TEST1).addError(FIELD_TEST1, MESSAGE_TEST1);
        ErrorInfo errorInfo2 = createErrorInfo(URL_TEST1).addError(FIELD_TEST1, MESSAGE_TEST1);

        assertTrue(errorInfo1.equals(errorInfo2) && errorInfo2.equals(errorInfo1));
    }

    @Test
    public void testNotEqualsWithDifferentsFields() {
        ErrorInfo errorInfo1 = createErrorInfo(URL_TEST1).addError(FIELD_TEST1, MESSAGE_TEST1);
        ErrorInfo errorInfo2 = createErrorInfo(URL_TEST1).addError(FIELD_TEST2, MESSAGE_TEST2);

        assertFalse(errorInfo1.equals(errorInfo2) || errorInfo2.equals(errorInfo1));
    }

    @Test
    public void testNotEqualsWithDifferentsObjects() {
        ErrorInfo errorInfo = createErrorInfo(URL_TEST1);

        assertFalse(errorInfo.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        ErrorInfo errorInfo1 = createErrorInfo(URL_TEST1).addError(FIELD_TEST1, MESSAGE_TEST1);
        ErrorInfo errorInfo2 = createErrorInfo(URL_TEST1).addError(FIELD_TEST1, MESSAGE_TEST1);

        assertEquals(errorInfo1.hashCode(), errorInfo2.hashCode());
    }

    @Test
    public void testHasCodeWithDifferentFields() {
        ErrorInfo errorInfo1 = createErrorInfo(URL_TEST1).addError(FIELD_TEST1, MESSAGE_TEST1);
        ErrorInfo errorInfo2 = createErrorInfo(URL_TEST1).addError(FIELD_TEST2, MESSAGE_TEST2);

        assertNotEquals(errorInfo1.hashCode(), errorInfo2.hashCode());
    }

    @Test
    public void testToString() {
        ErrorInfo errorInfo = createErrorInfo(null);
        String errorInfoString = errorInfo.toString();

        assertThatErrorInfoStringContainsAllFields(errorInfoString);
    }

    private void assertThatErrorInfoStringContainsAllFields(String errorInfoString) {
        assertThat(errorInfoString, containsString("url=null"));
        assertThat(errorInfoString, containsString("errors=[]"));
    }

    private ErrorInfo createErrorInfo(String url) {
        return new ErrorInfo().setUrl(url);
    }
}