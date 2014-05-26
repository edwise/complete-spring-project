package com.edwise.completespring.entities;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by user EAnton on 28/04/2014.
 */
public class FooTest {

    @Test
    public void testCopyFrom() {
        Foo fooFrom = new Foo().setId(1l).setSampleTextAttribute("AttText1").setSampleLocalDateAttribute(new LocalDate());
        Foo foo = new Foo().setId(15l);

        assertEquals("No son iguales", foo.copyFrom(fooFrom), fooFrom);
    }

    @Test
    public void testEquals() {
        // same fields
        LocalDate date = new LocalDate();
        Foo foo1 = new Foo().setId(1l).setSampleTextAttribute("AttText1").setSampleLocalDateAttribute(date);
        Foo foo2 = new Foo().setId(1l).setSampleTextAttribute("AttText1").setSampleLocalDateAttribute(date);
        assertTrue("Deben ser iguales", foo1.equals(foo2) && foo2.equals(foo1));

        // same id, different text
        foo1 = new Foo().setId(1l).setSampleTextAttribute("AttText1");
        foo2 = new Foo().setId(1l).setSampleTextAttribute("AttText2");
        assertFalse("No deben ser iguales", foo1.equals(foo2) || foo2.equals(foo1));

        // different object
        assertFalse("No deben ser iguales", new Foo().setId(1l).equals(new Object()));
    }

    @Test
    public void testHashCode() {
        // same fields
        LocalDate date = new LocalDate();
        Foo foo1 = new Foo().setId(1l).setSampleTextAttribute("AttText1").setSampleLocalDateAttribute(date);
        Foo foo2 = new Foo().setId(1l).setSampleTextAttribute("AttText1").setSampleLocalDateAttribute(date);
        assertEquals("Deben ser iguales", foo1.hashCode(), foo2.hashCode());

        // different fields
        foo1 = new Foo().setId(1l).setSampleTextAttribute("AttText1").setSampleLocalDateAttribute(date);
        foo2 = new Foo().setId(3l).setSampleTextAttribute("AttText3").setSampleLocalDateAttribute(date);
        assertNotEquals("No deben ser iguales", foo1.hashCode(), foo2.hashCode());
    }

    @Test
    public void testToString() {
        Foo foo = new Foo();
        assertTrue(foo.toString().contains("id=null"));
        assertTrue(foo.toString().contains("sampleTextAttribute=null"));
        assertTrue(foo.toString().contains("sampleLocalDateAttribute=null"));
    }
}
