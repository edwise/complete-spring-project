package com.edwise.completespring.entities;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by user EAnton on 28/04/2014.
 */
public class BookTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testCopyFrom() {
        Book bookFrom = new Book(3l, "Libro prueba", Arrays.asList(new Author().setName("Edu")), "11-333-12", new LocalDate(),
                new Publisher().setName("Editorial 1").setCountry("ES").setOnline(true));
        Book book = new Book();

        book.copyFrom(bookFrom);

        assertEquals("No son iguales", book, bookFrom);
    }

    @Test
    public void testEquals() {
        LocalDate date = new LocalDate();
        Book book1 = new Book(3l, "Libro prueba", Arrays.asList(new Author().setName("Edu")), "11-333-12", date,
                new Publisher().setName("Editorial 1").setCountry("ES").setOnline(true));
        Book book2 = new Book(3l, "Libro prueba", Arrays.asList(new Author().setName("Edu")), "11-333-12", date,
                new Publisher().setName("Editorial 1").setCountry("ES").setOnline(true));
        assertTrue("Deben ser iguales", book1.equals(book2) && book2.equals(book1));

        // different fields
        book1 = new Book(3l, "Libro prueba", Arrays.asList(new Author().setName("Edu")), "11-333-12", date,
                new Publisher().setName("Editorial 1").setCountry("ES").setOnline(true));
        book2 = new Book(3l, "Libro prueba2", Arrays.asList(new Author().setName("Edu")), "11-111-12", date,
                new Publisher().setName("Editorial 3").setCountry("ES").setOnline(false));
        assertFalse("No deben ser iguales", book1.equals(book2) || book2.equals(book1));

        // different object
        assertFalse("No deben ser iguales", new Book().setId(2l).equals(new Object()));
    }

    @Test
    public void testHashCode() {
        LocalDate date = new LocalDate();
        Book book1 = new Book(3l, "Libro prueba", Arrays.asList(new Author().setName("Edu")), "11-333-12", date,
                new Publisher().setName("Editorial 1").setCountry("ES").setOnline(true));
        Book book2 = new Book(3l, "Libro prueba", Arrays.asList(new Author().setName("Edu")), "11-333-12", date,
                new Publisher().setName("Editorial 1").setCountry("ES").setOnline(true));
        assertEquals("Deben ser iguales", book1.hashCode(), book2.hashCode());

        // different fields
        book1 = new Book(3l, "Libro prueba", Arrays.asList(new Author().setName("Edu")), "11-333-12", date,
                new Publisher().setName("Editorial 1").setCountry("ES").setOnline(true));
        book2 = new Book(3l, "Libro prueba2", Arrays.asList(new Author().setName("Edu")), "11-111-12", date,
                new Publisher().setName("Editorial 3").setCountry("ES").setOnline(false));
        assertNotEquals("No deben ser iguales", book1.hashCode(), book2.hashCode());
    }

    @Test
    public void testToString() {
        Book book = new Book();
        assertTrue(book.toString().contains("id=null"));
        assertTrue(book.toString().contains("title='null'"));
        assertTrue(book.toString().contains("authors=null"));
        assertTrue(book.toString().contains("isbn='null'"));
        assertTrue(book.toString().contains("releaseDate=null"));
        assertTrue(book.toString().contains("publisher=null"));
    }
}
