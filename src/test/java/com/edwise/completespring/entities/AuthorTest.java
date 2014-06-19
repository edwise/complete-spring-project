package com.edwise.completespring.entities;

import org.junit.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by user EAnton on 28/04/2014.
 */
public class AuthorTest {
    private static final String NAME_TEST1 = "J.";
    private static final String NAME_TEST2 = "Stephen";
    private static final String SURNAME_TEST1 = "Tolkien";
    private static final String SURNAME_TEST2 = "King";

    @Test
    public void testCopyFrom() {
        Author authorFrom = createAuthor(NAME_TEST1, SURNAME_TEST1);
        Author author = createAuthor(null, null);

        author.copyFrom(authorFrom);

        assertEquals("No son iguales", author, authorFrom);
    }

    @Test
    public void testEquals() {
        Author author1 = createAuthor(NAME_TEST1, SURNAME_TEST1);
        Author author2 = createAuthor(NAME_TEST1, SURNAME_TEST1);

        assertTrue("Deben ser iguales", author1.equals(author2) && author2.equals(author1));
    }

    @Test
    public void testNotEqualsWithDifferentsFields() {
        Author author1 = createAuthor(NAME_TEST1, SURNAME_TEST1);
        Author author2 = createAuthor(NAME_TEST1, SURNAME_TEST2);

        assertFalse("No deben ser iguales", author1.equals(author2) || author2.equals(author1));
    }

    @Test
    public void testNotEqualsWithDifferentsObjects() {
        Author author = createAuthor(NAME_TEST1, null);

        assertFalse("No deben ser iguales", author.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        Author author1 = createAuthor(NAME_TEST1, SURNAME_TEST1);
        Author author2 = createAuthor(NAME_TEST1, SURNAME_TEST1);

        assertEquals("Deben ser iguales", author1.hashCode(), author2.hashCode());
    }

    @Test
    public void testHasCodeWithDifferentFields() {
        Author author1 = createAuthor(NAME_TEST1, SURNAME_TEST1);
        Author author2 = createAuthor(NAME_TEST2, SURNAME_TEST2);

        assertNotEquals("No deben ser iguales", author1.hashCode(), author2.hashCode());
    }

    @Test
    public void testToString() {
        Author author = createAuthor(null, null);
        String authorString = author.toString();

        assertThatAuthorStringContainsAllFields(authorString);
    }

    private void assertThatAuthorStringContainsAllFields(String authorString) {
        assertThat(authorString, containsString("name=null"));
        assertThat(authorString, containsString("surname=null"));
    }

    public static Author createAuthor(String name, String surname) {
        return new Author()
                .setName(name)
                .setSurname(surname);
    }
}
