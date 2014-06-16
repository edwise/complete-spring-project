package com.edwise.completespring.exceptions.helpers;

import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorItemTest {

    // TODO refactorizar tests...

    @Test
    public void testEquals() {
        // same fields
        ErrorItem errorItem1 = new ErrorItem().setField("Field1").setMessage("MessageText1");
        ErrorItem errorItem2 = new ErrorItem().setField("Field1").setMessage("MessageText1");
        assertTrue("Deben ser iguales", errorItem1.equals(errorItem2) && errorItem2.equals(errorItem1));

        // same id, different text
        errorItem1 = new ErrorItem().setField("Field1").setMessage("MessageText1");
        errorItem2 = new ErrorItem().setField("Field1").setMessage("MessageText2");
        assertFalse("No deben ser iguales", errorItem1.equals(errorItem2) || errorItem2.equals(errorItem1));

        // different object
        assertFalse("No deben ser iguales", new ErrorItem().setField("Field1").equals(new Object()));
    }

    @Test
    public void testHashCode() {
        // same fields
        ErrorItem errorItem1 = new ErrorItem().setField("Field1").setMessage("MessageText1");
        ErrorItem errorItem2 = new ErrorItem().setField("Field1").setMessage("MessageText1");
        assertEquals("Deben ser iguales", errorItem1.hashCode(), errorItem2.hashCode());

        // different fields
        errorItem1 = new ErrorItem().setField("Field1").setMessage("MessageText1");
        errorItem2 = new ErrorItem().setField("Field1").setMessage("MessageText2");
        assertNotEquals("No deben ser iguales", errorItem1.hashCode(), errorItem2.hashCode());
    }

    @Test
    public void testToString() {
        ErrorItem errorItem = new ErrorItem();
        assertTrue(errorItem.toString().contains("field=null"));
        assertTrue(errorItem.toString().contains("message=null"));
    }

}