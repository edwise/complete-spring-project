package com.edwise.completespring.entities;

import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class FooTest {
    private static final long ID_TEST1 = 123L;
    private static final long ID_TEST2 = 456L;
    private static final String TEXT_ATTR_TEST1 = "AttText1";
    private static final String TEXT_ATTR_TEST2 = "AttText2";
    private static final LocalDate DATE_TEST1 = LocalDate.of(2013, 1, 26);

    @Test
    public void testCopyFrom() {
        Foo fooFrom = createFoo(ID_TEST1, TEXT_ATTR_TEST1, DATE_TEST1);
        Foo foo = createFoo(ID_TEST2, null, null);

        foo.copyFrom(fooFrom);

        assertThat(foo).isEqualTo(fooFrom);
    }

    @Test
    public void testEquals() {
        Foo foo1 = createFoo(ID_TEST1, TEXT_ATTR_TEST1, DATE_TEST1);
        Foo foo2 = createFoo(ID_TEST1, TEXT_ATTR_TEST1, DATE_TEST1);

        assertThat(foo1.equals(foo2) && foo2.equals(foo1)).isTrue();
    }

    @Test
    public void testNotEqualsWithDifferentsFields() {
        Foo foo1 = createFoo(ID_TEST1, TEXT_ATTR_TEST1, null);
        Foo foo2 = createFoo(ID_TEST1, TEXT_ATTR_TEST2, null);

        assertThat(foo1.equals(foo2) || foo2.equals(foo1)).isFalse();
    }

    @Test
    public void testNotEqualsWithDifferentsObjects() {
        Foo foo = createFoo(ID_TEST1, null, null);

        assertThat(foo).isNotEqualTo(new Object());
    }

    @Test
    public void testHashCode() {
        Foo foo1 = createFoo(ID_TEST1, TEXT_ATTR_TEST1, DATE_TEST1);
        Foo foo2 = createFoo(ID_TEST1, TEXT_ATTR_TEST1, DATE_TEST1);

        assertThat(foo1.hashCode()).isEqualTo(foo2.hashCode());
    }

    @Test
    public void testHasCodeWithDifferentFields() {
        Foo foo1 = createFoo(ID_TEST1, TEXT_ATTR_TEST1, DATE_TEST1);
        Foo foo2 = createFoo(ID_TEST2, TEXT_ATTR_TEST2, DATE_TEST1);

        assertThat(foo1.hashCode()).isNotEqualTo(foo2.hashCode());
    }

    @Test
    public void testToString() {
        Foo foo = createFoo(null, null, null);

        assertThatFooStringContainsAllFields(foo.toString());
    }

    private void assertThatFooStringContainsAllFields(String fooString) {
        assertThat(fooString).contains("id=null");
        assertThat(fooString).contains("sampleTextAttribute=null");
        assertThat(fooString).contains("sampleLocalDateAttribute=null");
    }

    public static Foo createFoo(Long id, String textAttribute, LocalDate localDateAttribute) {
        return new Foo()
                .setId(id)
                .setSampleTextAttribute(textAttribute)
                .setSampleLocalDateAttribute(localDateAttribute);
    }
}
