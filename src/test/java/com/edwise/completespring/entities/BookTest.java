package com.edwise.completespring.entities;

import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Created by user EAnton on 28/04/2014.
 */
public class BookTest {

    // TODO refactor de news de publisher y author, en un metodo factory (con parametros)

    private static final long BOOK_ID_TEST1 = 31l;
    private static final String BOOK_TITLE_TEST1 = "Libro prueba";
    private static final String BOOK_TITLE_TEST2 = "Libro prueba2";
    private static final String AUTHOR_NAME_TEST1 = "Edu";
    private static final String AUTHOR_SURNAME_TEST1 = "Surname";
    private static final String BOOK_ISBN_TEST1 = "11-333-12";
    private static final String BOOK_ISBN_TEST2 = "21-929-34";
    private static final String PUBLISHER_NAME_TEST1 = "Editorial 1";
    private static final String PUBLISHER_NAME_TEST2 = "Editorial 3";
    private static final String PUBLISHER_COUNTRY_TEST1 = "ES";

    @Test
    public void testCopyFrom() {
        Book bookFrom = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(new LocalDate())
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();
        Book bookTo = new BookBuilder().build();

        bookTo.copyFrom(bookFrom);

        assertEquals("No son iguales", bookTo, bookFrom);
    }

    @Test
    public void testEquals() {
        LocalDate date = new LocalDate();
        Book book1 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(date)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();
        Book book2 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(date)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();

        assertTrue("Deben ser iguales", book1.equals(book2) && book2.equals(book1));
    }

    @Test
    public void testNotEqualsWithDifferentsFields() {
        LocalDate date = new LocalDate();
        Book book1 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(date)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();
        Book book2 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST2)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST2)
                .releaseDate(date)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST2).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
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
        LocalDate date = new LocalDate();
        Book book1 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(date)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();
        Book book2 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(date)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();

        assertEquals("Deben ser iguales", book1.hashCode(), book2.hashCode());
    }

    @Test
    public void testHasCodeWithDifferentFields() {
        LocalDate date = new LocalDate();
        Book book1 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(date)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();
        Book book2 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST2)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST2)
                .releaseDate(date)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST2).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
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
}
