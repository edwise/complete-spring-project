package com.edwise.completespring.controllers;

import com.edwise.completespring.assemblers.BookResource;
import com.edwise.completespring.assemblers.BookResourceAssembler;
import com.edwise.completespring.entities.Author;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.Publisher;
import com.edwise.completespring.exceptions.InvalidRequestException;
import com.edwise.completespring.exceptions.NotFoundException;
import com.edwise.completespring.services.BookService;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by user EAnton on 04/04/2014.
 */
public class BookControllerTest {

    private BookController controller;

    private MockHttpServletRequest request;

    @Mock
    BookService bookService;

    @Mock
    BindingResult errors;


    @Before
    public void init() {
        this.request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));
        MockitoAnnotations.initMocks(this);
        this.controller = new BookController();
        ReflectionTestUtils.setField(this.controller, "bookService", this.bookService); // inject bookService
        ReflectionTestUtils.setField(controller, "bookResourceAssembler", new BookResourceAssembler());
    }

    @After
    public void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test(expected = InvalidRequestException.class)
    public void testUpdateInvalidRequest() {
        Long id = 1l;
        Book bookReq = new Book().setTitle("Sample Text").setReleaseDate(new LocalDate());
        Book bookResp = new Book().copyFrom(bookReq).setId(1l);
        when(errors.hasErrors()).thenReturn(true);
        when(bookService.save(bookReq)).thenReturn(bookResp);
        controller.updateBook(id, bookReq, errors);
        verify(errors, times(1)).hasErrors();
        fail("Expected exception");
    }


    @Test
    public void testCreate() {
        Book bookReq = new Book(-1l, "Libro prueba", Arrays.asList(new Author().setName("Edu")), "11-333-12", new LocalDate(), new Publisher().setName("Editorial 1").setCountry("ES").setOnline(false));
        Book bookResp = new Book().copyFrom(bookReq).setId(1l);
        when(errors.hasErrors()).thenReturn(false);
        when(bookService.create(bookReq)).thenReturn(bookResp);
        controller.createBook(bookReq, errors);
        verify(errors, times(1)).hasErrors();
        verify(bookService, times(1)).create(bookReq);
        assertFalse(errors.hasErrors());
    }

    @Test(expected = InvalidRequestException.class)
    public void testCreateInvalidRequest() {
        Book bookReq = new Book();
        when(errors.hasErrors()).thenReturn(true);
        controller.createBook(bookReq, errors);
        verify(errors, times(1)).hasErrors();
    }

    @Test
    public void testUpdate() {
        Long id = 1l;
        Book bookReq = new Book(1l, "Libro prueba", Arrays.asList(new Author().setName("Edu")), "11-333-12", new LocalDate(), new Publisher().setName("Editorial 1").setCountry("ES").setOnline(false));
        Book fooDB = new Book().setId(1l);
        when(bookService.findOne(id)).thenReturn(fooDB);
        when(bookService.save(fooDB.copyFrom(bookReq))).thenReturn(fooDB.copyFrom(bookReq));
        controller.updateBook(id, bookReq, errors);
        verify(bookService, times(1)).findOne(id);
        verify(bookService, times(1)).save(fooDB.copyFrom(bookReq));
    }


    @Test
    public void testGet() {
        Long id = 1l;
        String sampleTitle = "Sample Text";
        String sampleIsbn = "Sample Isbn";
        List<Author> sampleAuthors = Arrays.asList(new Author().setName("SampleAuthor"));
        LocalDate sampleDate = new LocalDate();
        Publisher samplePublisher = new Publisher().setName("Editorial 1").setCountry("ES").setOnline(false);

        Book bookReq = new Book(id, sampleTitle, sampleAuthors, sampleIsbn, sampleDate, samplePublisher);
        when(bookService.findOne(id)).thenReturn(bookReq);
        ResponseEntity<BookResource> result = controller.getBook(id);
        verify(bookService, times(1)).findOne(id);
        assertEquals("http://localhost/api/book/1", result.getBody().getLink("self").getHref());
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() {
        Long id = 1000l;
        when(bookService.findOne(id)).thenThrow(new NotFoundException());
        controller.getBook(id);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() {
        Long id = 1l;
        Book bookReq = new Book(1l, "Libro prueba", Arrays.asList(new Author().setName("Edu")), "11-333-12", new LocalDate(),
                new Publisher().setName("Editorial 1").setCountry("ES").setOnline(false));
        when(bookService.findOne(id)).thenThrow(new NotFoundException());
        controller.updateBook(id, bookReq, errors);
    }

    @Test
    public void testDelete() {
        Long id = 1l;
        doNothing().when(bookService).delete(id);
        controller.deleteBook(id);
        verify(bookService, times(1)).delete(id);
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void testFindAll() {
        List books = Arrays.asList(new Book(3l, "Libro prueba", Arrays.asList(new Author().setName("Edu")), "11-333-12", new LocalDate(),
                        new Publisher().setName("Editorial 1").setCountry("ES").setOnline(false)),
                new Book(400l, "Libro prueba 2", Arrays.asList(new Author().setName("Otro"), new Author().setName("S. King")),
                        "12-1234-12",
                        new LocalDate(), new Publisher().setName("Editorial 2").setCountry("UK").setOnline(true)),
                new Book(14l, "Libro prueba 3", Arrays.asList(new Author().setName("Nadie")), "12-9999-92", new LocalDate(),
                        new Publisher().setName("Editorial 4").setCountry("ES").setOnline(false))
        );

        when(bookService.findAll()).thenReturn(books);
        ResponseEntity<List<BookResource>> result = controller.getAllBooks();
        assertNotNull(result.getBody());
    }
}
