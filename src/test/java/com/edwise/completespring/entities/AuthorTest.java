package com.edwise.completespring.entities;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(author).isEqualTo(authorFrom);
    }

    @Test
    public void testEquals() {
        Author author1 = createAuthor(NAME_TEST1, SURNAME_TEST1);
        Author author2 = createAuthor(NAME_TEST1, SURNAME_TEST1);

        assertThat(author1.equals(author2) && author2.equals(author1)).isTrue();
    }

    @Test
    public void testNotEqualsWithDifferentsFields() {
        Author author1 = createAuthor(NAME_TEST1, SURNAME_TEST1);
        Author author2 = createAuthor(NAME_TEST1, SURNAME_TEST2);

        assertThat(author1.equals(author2) || author2.equals(author1)).isFalse();
    }

    @Test
    public void testNotEqualsWithDifferentsObjects() {
        Author author = createAuthor(NAME_TEST1, null);

        assertThat(author).isNotEqualTo(new Object());
    }

    @Test
    public void testHashCode() {
        Author author1 = createAuthor(NAME_TEST1, SURNAME_TEST1);
        Author author2 = createAuthor(NAME_TEST1, SURNAME_TEST1);

        assertThat(author1.hashCode()).isEqualTo(author2.hashCode());
    }

    @Test
    public void testHasCodeWithDifferentFields() {
        Author author1 = createAuthor(NAME_TEST1, SURNAME_TEST1);
        Author author2 = createAuthor(NAME_TEST2, SURNAME_TEST2);

        assertThat(author1.hashCode()).isNotEqualTo(author2.hashCode());
    }

    @Test
    public void testToString() {
        Author author = createAuthor(null, null);

        assertThatAuthorStringContainsAllFields(author.toString());
    }

    private void assertThatAuthorStringContainsAllFields(String authorString) {
        assertThat(authorString).contains("name=null");
        assertThat(authorString).contains("surname=null");
    }

    public static Author createAuthor(String name, String surname) {
        return new Author()
                .setName(name)
                .setSurname(surname);
    }
}
