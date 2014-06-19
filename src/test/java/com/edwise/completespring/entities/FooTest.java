package com.edwise.completespring.entities;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by user EAnton on 28/04/2014.
 */
public class FooTest {
    private static final long ID_TEST1 = 123l;
    private static final long ID_TEST2 = 456l;
    private static final String TEXT_ATTR_TEST1 = "AttText1";
    private static final String TEXT_ATTR_TEST2 = "AttText2";
    private static final LocalDate DATE_TEST1 = new LocalDate(2013, 1, 26);

    @Test
    public void testCopyFrom() {
        Foo fooFrom = createFoo(ID_TEST1, TEXT_ATTR_TEST1, DATE_TEST1);
        Foo foo = createFoo(ID_TEST2, null, null);

        assertEquals("No son iguales", foo.copyFrom(fooFrom), fooFrom);
    }

    @Test
    public void testEquals() {
        Foo foo1 = createFoo(ID_TEST1, TEXT_ATTR_TEST1, DATE_TEST1);
        Foo foo2 = createFoo(ID_TEST1, TEXT_ATTR_TEST1, DATE_TEST1);

        assertTrue("Deben ser iguales", foo1.equals(foo2) && foo2.equals(foo1));
    }

    @Test
    public void testNotEqualsWithDifferentsFields() {
        Foo foo1 = createFoo(ID_TEST1, TEXT_ATTR_TEST1, null);
        Foo foo2 = createFoo(ID_TEST1, TEXT_ATTR_TEST2, null);

        assertFalse("No deben ser iguales", foo1.equals(foo2) || foo2.equals(foo1));
    }

    @Test
    public void testNotEqualsWithDifferentsObjects() {
        Foo foo = createFoo(ID_TEST1, null, null);

        assertFalse("No deben ser iguales", foo.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        Foo foo1 = createFoo(ID_TEST1, TEXT_ATTR_TEST1, DATE_TEST1);
        Foo foo2 = createFoo(ID_TEST1, TEXT_ATTR_TEST1, DATE_TEST1);

        assertEquals("Deben ser iguales", foo1.hashCode(), foo2.hashCode());
    }

    @Test
    public void testHasCodeWithDifferentFields() {
        Foo foo1 = createFoo(ID_TEST1, TEXT_ATTR_TEST1, DATE_TEST1);
        Foo foo2 = createFoo(ID_TEST2, TEXT_ATTR_TEST2, DATE_TEST1);

        assertNotEquals("No deben ser iguales", foo1.hashCode(), foo2.hashCode());
    }

    @Test
    public void testToString() {
        Foo foo = createFoo(null, null, null);
        String fooString = foo.toString();

        assertThatFooStringContainsAllFields(fooString);
    }

    private void assertThatFooStringContainsAllFields(String fooString) {
        assertThat(fooString, containsString("id=null"));
        assertThat(fooString, containsString("sampleTextAttribute=null"));
        assertThat(fooString, containsString("sampleLocalDateAttribute=null"));
    }

    public static Foo createFoo(Long id, String textAttribute, LocalDate localDateAttribute) {
        return new Foo()
                .setId(id)
                .setSampleTextAttribute(textAttribute)
                .setSampleLocalDateAttribute(localDateAttribute);
    }
}
