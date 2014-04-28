package com.edwise.completespring.entities;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user EAnton on 28/04/2014.
 */
public class FooTest {

    @Before
    public void init() {
    }

    @Test
    public void testCopyFrom() {
        Foo fooFrom = new Foo().setId(1l).setSampleTextAttribute("AttText1").setSampleLocalDateAttribute(new LocalDate());
        Foo foo = new Foo().setId(15l);

        foo.copyFrom(fooFrom);

        assertEquals("No son iguales", foo, fooFrom);
    }
}
