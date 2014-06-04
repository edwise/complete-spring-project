package com.edwise.completespring.exceptions.helpers;

import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorInfoTest {

    @Test
    public void testAddError() {
        ErrorInfo errorInfo = new ErrorInfo().addError("Field1", "MessageText").addError("Field2",
                "MessageText2").addError("Field3", "MessageText3");
        assertNotNull("No puede ser nulo", errorInfo.getErrors());
        assertEquals("Deben ser igual", errorInfo.getErrors().size(), 3);
    }

    @Test
    public void testEquals() {
        // same fields
        ErrorInfo errorInfo1 = new ErrorInfo().setUrl("URL_test").addError("Field1", "MessageText");
        ErrorInfo errorInfo2 = new ErrorInfo().setUrl("URL_test").addError("Field1", "MessageText");
        assertTrue("Deben ser iguales", errorInfo1.equals(errorInfo2) && errorInfo2.equals(errorInfo1));

        // same id, different fields
        errorInfo1 = new ErrorInfo().setUrl("URL_test").addError("Field1", "MessageText");
        errorInfo2 = new ErrorInfo().setUrl("URL_test").addError("Field2", "MessageText2");
        assertFalse("No deben ser iguales", errorInfo1.equals(errorInfo2) || errorInfo2.equals(errorInfo1));

        // different object
        assertFalse("No deben ser iguales", new ErrorInfo().setUrl("URL_test").equals(new Object()));
    }

    @Test
    public void testHashCode() {
        // same fields
        ErrorInfo errorInfo1 = new ErrorInfo().setUrl("URL_test").addError("Field1", "MessageText");
        ErrorInfo errorInfo2 = new ErrorInfo().setUrl("URL_test").addError("Field1", "MessageText");
        assertEquals("Deben ser iguales", errorInfo1.hashCode(), errorInfo2.hashCode());

        // different fields
        errorInfo1 = new ErrorInfo().setUrl("URL_test").addError("Field1", "MessageText");
        errorInfo2 = new ErrorInfo().setUrl("URL_test").addError("Field2", "MessageText2");
        assertNotEquals("No deben ser iguales", errorInfo1.hashCode(), errorInfo2.hashCode());
    }

    @Test
    public void testToString() {
        ErrorInfo errorInfo = new ErrorInfo();
        assertTrue(errorInfo.toString().contains("url=null"));
        assertTrue(errorInfo.toString().contains("errors=null"));
    }
}