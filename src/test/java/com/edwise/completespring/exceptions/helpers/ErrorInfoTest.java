package com.edwise.completespring.exceptions.helpers;

import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorInfoTest {

    @Test
    public void testEquals() {
        // same fields
        ErrorInfo errorInfo1 = new ErrorInfo().setUrl("URL_test").setMessage("Message test");
        ErrorInfo errorInfo2 = new ErrorInfo().setUrl("URL_test").setMessage("Message test");
        assertTrue("Deben ser iguales", errorInfo1.equals(errorInfo2) && errorInfo2.equals(errorInfo1));

        // same id, different text
        errorInfo1 = new ErrorInfo().setUrl("URL_test").setMessage("Message test");
        errorInfo2 = new ErrorInfo().setUrl("URL_test").setMessage("Message test different");
        assertFalse("No deben ser iguales", errorInfo1.equals(errorInfo2) || errorInfo2.equals(errorInfo1));

        // different object
        assertFalse("No deben ser iguales", new ErrorInfo().setUrl("URL_test").equals(new Object()));
    }

    @Test
    public void testHashCode() {
        // same fields
        ErrorInfo errorInfo1 = new ErrorInfo().setUrl("URL_test").setMessage("Message test");
        ErrorInfo errorInfo2 = new ErrorInfo().setUrl("URL_test").setMessage("Message test");
        assertEquals("Deben ser iguales", errorInfo1.hashCode(), errorInfo2.hashCode());

        // different fields
        errorInfo1 = new ErrorInfo().setUrl("URL_test").setMessage("Message test");
        errorInfo2 = new ErrorInfo().setUrl("URL_test").setMessage("Message test different");
        assertNotEquals("No deben ser iguales", errorInfo1.hashCode(), errorInfo2.hashCode());
    }

    @Test
    public void testToString() {
        ErrorInfo errorInfo = new ErrorInfo();
        assertTrue(errorInfo.toString().contains("url=null"));
        assertTrue(errorInfo.toString().contains("message=null"));
    }
}