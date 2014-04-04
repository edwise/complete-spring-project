package com.edwise.completespring.controllers;

import com.edwise.completespring.entities.Book;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by user EAnton on 04/04/2014.
 */
public class BookControllerTest {

    private BookController bookController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        bookController = new BookController();
    }

    @Test
    public void testGetBook() throws Exception {
        Book book = bookController.getBook(1000);
        assertTrue(book.getId() == 1000);
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = bookController.getAllBooks();

        assertTrue(books.size() > 0);
    }

    @Test
    public void testCreateBook() {
        bookController.createBook(new Book(3000, "Libro prueba", Arrays.asList("Edu"), "12-2344-12", new LocalDate()));

        assertTrue(true);
    }

    @Test
    public void testUpdateBook() {
        bookController.updateBook(2001, new Book(-1, "Libro prueba update", Arrays.asList("Edu"), "12-2344-12", new LocalDate()));

        assertTrue(true);
    }

    @Test
    public void testDeleteBook() {
        bookController.deleteBook(2001);

        assertTrue(true);
    }
}
