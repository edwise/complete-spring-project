package com.edwise.completespring.exceptions.helpers;

import org.junit.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class ErrorItemTest {
    private static final String FIELD_TEST1 = "authorName";
    private static final String MESSAGE_TEXT1 = "El campo no puede ser nulo";
    private static final String MESSAGE_TEXT2 = "El campo no puede venir vacio";

    @Test
    public void testEquals() {
        ErrorItem errorItem1 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT1);
        ErrorItem errorItem2 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT1);

        assertTrue(errorItem1.equals(errorItem2) && errorItem2.equals(errorItem1));
    }

    @Test
    public void testNotEqualsWithDifferentFields() {
        ErrorItem errorItem1 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT1);
        ErrorItem errorItem2 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT2);

        assertFalse(errorItem1.equals(errorItem2) || errorItem2.equals(errorItem1));
    }

    @Test
    public void testNotEqualsWithDifferentObjects() {
        ErrorItem errorItem = createErrorItem(FIELD_TEST1, null);

        assertFalse(errorItem.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        ErrorItem errorItem1 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT1);
        ErrorItem errorItem2 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT1);

        assertEquals(errorItem1.hashCode(), errorItem2.hashCode());
    }

    @Test
    public void testHasCodeWithDifferentFields() {
        ErrorItem errorItem1 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT1);
        ErrorItem errorItem2 = createErrorItem(FIELD_TEST1, MESSAGE_TEXT2);

        assertNotEquals(errorItem1.hashCode(), errorItem2.hashCode());
    }

    @Test
    public void testToString() {
        ErrorItem errorItem = createErrorItem(null, null);

        String errorItemString = errorItem.toString();

        assertThatErrorItemStringContainsAllFields(errorItemString);
    }

    private void assertThatErrorItemStringContainsAllFields(String errorItemString) {
        assertThat(errorItemString, containsString("field=null"));
        assertThat(errorItemString, containsString("message=null"));
    }

    private ErrorItem createErrorItem(String field, String message) {
        return new ErrorItem()
                .setField(field)
                .setMessage(message);
    }

}