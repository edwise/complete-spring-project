package com.edwise.completespring.entities;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by user EAnton on 28/04/2014.
 */
public class BookTest {

    @Before
    public void init() {
    }

    @Test
    public void testCopyFrom() {
        Book bookFrom = new Book(3l, "Libro prueba", Arrays.asList(new Author().setName("Edu")), "11-333-12", new LocalDate(),
                new Publisher().setName("Editorial 1").setCountry("ES").setOnline(true));
        Book book = new Book();

        book.copyFrom(bookFrom);

        assertEquals("No son iguales", book, bookFrom);
    }
}
