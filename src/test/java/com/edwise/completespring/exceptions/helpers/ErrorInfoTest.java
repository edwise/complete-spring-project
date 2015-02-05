package com.edwise.completespring.exceptions.helpers;

import org.junit.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ErrorInfoTest {
    private static final String FIELD_TEST1 = "Field1";
    private static final String FIELD_TEST2 = "Field2";
    private static final String MESSAGE_TEST1 = "MessageText";
    private static final String MESSAGE_TEST2 = "MessageText2";
    private static final String URL_TEST1 = "URL_test";
    private static final int TWO_ITEMS = 2;

    @Test
    public void testAddError() {
        ErrorInfo errorInfo = createErrorInfo(null).addError(FIELD_TEST1, MESSAGE_TEST1).addError(FIELD_TEST2,
                MESSAGE_TEST2);

        assertNotNull(errorInfo.getErrors());
        assertEquals(TWO_ITEMS, errorInfo.getErrors().size());
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
        assertThat(errorInfoString, containsString("errors=null"));
    }

    private ErrorInfo createErrorInfo(String url) {
        return new ErrorInfo()
                .setUrl(url);
    }
}