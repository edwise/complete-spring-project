package com.edwise.completespring.exceptions.helpers;

import org.junit.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class ErrorItemTest {
    private static final String FIELD_TEST1 = "Field1";
    private static final String MESSAGE_TEXT1 = "MessageText1";
    private static final String MESSAGE_TEXT2 = "MessageText2";

    // TODO refactorizar tests...

    @Test
    public void testEquals() {
        ErrorItem errorItem1 = new ErrorItem().setField(FIELD_TEST1).setMessage(MESSAGE_TEXT1);
        ErrorItem errorItem2 = new ErrorItem().setField(FIELD_TEST1).setMessage(MESSAGE_TEXT1);

        assertTrue("Deben ser iguales", errorItem1.equals(errorItem2) && errorItem2.equals(errorItem1));
    }

    @Test
    public void testNotEqualsWithDifferentFields() {
        ErrorItem errorItem1 = new ErrorItem().setField(FIELD_TEST1).setMessage(MESSAGE_TEXT1);
        ErrorItem errorItem2 = new ErrorItem().setField(FIELD_TEST1).setMessage(MESSAGE_TEXT2);

        assertFalse("No deben ser iguales", errorItem1.equals(errorItem2) || errorItem2.equals(errorItem1));
    }

    @Test
    public void testNotEqualsWithDifferentObjects() {
        assertFalse("No deben ser iguales", new ErrorItem().setField(FIELD_TEST1).equals(new Object()));
    }

    @Test
    public void testHashCode() {
        ErrorItem errorItem1 = new ErrorItem().setField(FIELD_TEST1).setMessage(MESSAGE_TEXT1);
        ErrorItem errorItem2 = new ErrorItem().setField(FIELD_TEST1).setMessage(MESSAGE_TEXT1);

        assertEquals("Deben ser iguales", errorItem1.hashCode(), errorItem2.hashCode());
    }

    @Test
    public void testHasCodeWithDifferentFields() {
        ErrorItem errorItem1 = new ErrorItem().setField(FIELD_TEST1).setMessage(MESSAGE_TEXT1);
        ErrorItem errorItem2 = new ErrorItem().setField(FIELD_TEST1).setMessage(MESSAGE_TEXT2);

        assertNotEquals("No deben ser iguales", errorItem1.hashCode(), errorItem2.hashCode());
    }

    @Test
    public void testToString() {
        ErrorItem errorItem = new ErrorItem();

        String errorItemString = errorItem.toString();

        assertThatErrorItemStringContainsAllFields(errorItemString);
    }

    private void assertThatErrorItemStringContainsAllFields(String errorItemString) {
        assertThat(errorItemString, containsString("field=null"));
        assertThat(errorItemString, containsString("message=null"));
    }

}