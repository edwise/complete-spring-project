package com.edwise.completespring.entities;

import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.hamcrest.core.StringContains.containsString;

public class BookTest {

    private static final long BOOK_ID_TEST1 = 31l;
    private static final String BOOK_TITLE_TEST1 = "Lord of the Rings";
    private static final String BOOK_TITLE_TEST2 = "Bautismo de Fuego";
    private static final String AUTHOR_NAME_TEST1 = "J.R.R.";
    private static final String AUTHOR_SURNAME_TEST1 = "Tolkien";
    private static final String BOOK_ISBN_TEST1 = "11-333-12";
    private static final String BOOK_ISBN_TEST2 = "21-929-34";
    private static final String PUBLISHER_NAME_TEST1 = "Editorial Planeta";
    private static final String PUBLISHER_NAME_TEST2 = "Gigamesh";
    private static final String PUBLISHER_COUNTRY_TEST1 = "ES";
    private static final LocalDate DATE_TEST1 = new LocalDate(2013, 1, 26);

    @Test
    public void testCopyFrom() {
        Book bookFrom = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(createAuthor(AUTHOR_NAME_TEST1, AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(DATE_TEST1)
                .publisher(createPublisher(PUBLISHER_NAME_TEST1, PUBLISHER_COUNTRY_TEST1, true))
                .build();
        Book bookTo = new BookBuilder().build();

        bookTo.copyFrom(bookFrom);

        assertEquals("No son iguales", bookTo, bookFrom);
    }

    @Test
    public void testEquals() {
        Book book1 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(createAuthor(AUTHOR_NAME_TEST1, AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(DATE_TEST1)
                .publisher(createPublisher(PUBLISHER_NAME_TEST1, PUBLISHER_COUNTRY_TEST1, true))
                .build();
        Book book2 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(createAuthor(AUTHOR_NAME_TEST1, AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(DATE_TEST1)
                .publisher(createPublisher(PUBLISHER_NAME_TEST1, PUBLISHER_COUNTRY_TEST1, true))
                .build();

        assertTrue("Deben ser iguales", book1.equals(book2) && book2.equals(book1));
    }

    @Test
    public void testNotEqualsWithDifferentsFields() {
        Book book1 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(createAuthor(AUTHOR_NAME_TEST1, AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(DATE_TEST1)
                .publisher(createPublisher(PUBLISHER_NAME_TEST1, PUBLISHER_COUNTRY_TEST1, true))
                .build();
        Book book2 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST2)
                .authors(Arrays.asList(createAuthor(AUTHOR_NAME_TEST1, AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST2)
                .releaseDate(DATE_TEST1)
                .publisher(createPublisher(PUBLISHER_NAME_TEST2, PUBLISHER_COUNTRY_TEST1, false))
                .build();

        assertFalse("No deben ser iguales", book1.equals(book2) || book2.equals(book1));
    }

    @Test
    public void testNotEqualsWithDifferentsObjects() {
        Book book = new BookBuilder().id(BOOK_ID_TEST1).build();
        assertFalse("No deben ser iguales", book.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        Book book1 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(createAuthor(AUTHOR_NAME_TEST1, AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(DATE_TEST1)
                .publisher(createPublisher(PUBLISHER_NAME_TEST1, PUBLISHER_COUNTRY_TEST1, true))
                .build();
        Book book2 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(createAuthor(AUTHOR_NAME_TEST1, AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(DATE_TEST1)
                .publisher(createPublisher(PUBLISHER_NAME_TEST1, PUBLISHER_COUNTRY_TEST1, true))
                .build();

        assertEquals("Deben ser iguales", book1.hashCode(), book2.hashCode());
    }

    @Test
    public void testHasCodeWithDifferentFields() {
        Book book1 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(createAuthor(AUTHOR_NAME_TEST1, AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(DATE_TEST1)
                .publisher(createPublisher(PUBLISHER_NAME_TEST1, PUBLISHER_COUNTRY_TEST1, true))
                .build();
        Book book2 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST2)
                .authors(Arrays.asList(createAuthor(AUTHOR_NAME_TEST1, AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST2)
                .releaseDate(DATE_TEST1)
                .publisher(createPublisher(PUBLISHER_NAME_TEST2, PUBLISHER_COUNTRY_TEST1, false))
                .build();

        assertNotEquals("No deben ser iguales", book1.hashCode(), book2.hashCode());
    }

    @Test
    public void testToString() {
        Book book = new BookBuilder().build();

        String bookString = book.toString();

        assertThatBookStringContainsAllFields(bookString);
    }

    private void assertThatBookStringContainsAllFields(String bookString) {
        assertThat(bookString, containsString("id=null"));
        assertThat(bookString, containsString("title=null"));
        assertThat(bookString, containsString("authors=null"));
        assertThat(bookString, containsString("isbn=null"));
        assertThat(bookString, containsString("releaseDate=null"));
        assertThat(bookString, containsString("publisher=null"));
    }

    private Author createAuthor(String name, String surname) {
        return new Author()
                .setName(name)
                .setSurname(surname);
    }

    private Publisher createPublisher(String name, String country, boolean isOnline) {
        return new Publisher()
                .setName(name)
                .setCountry(country)
                .setOnline(isOnline);
    }
}
