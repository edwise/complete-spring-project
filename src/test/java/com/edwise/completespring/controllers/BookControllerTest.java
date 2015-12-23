package com.edwise.completespring.controllers;

import com.edwise.completespring.assemblers.BookResource;
import com.edwise.completespring.assemblers.BookResourceAssembler;
import com.edwise.completespring.entities.Author;
import com.edwise.completespring.entities.AuthorTest;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.Publisher;
import com.edwise.completespring.entities.PublisherTest;
import com.edwise.completespring.exceptions.InvalidRequestException;
import com.edwise.completespring.exceptions.NotFoundException;
import com.edwise.completespring.services.BookService;
import com.edwise.completespring.testutil.BookBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {
    private static final long BOOK_ID_TEST1 = 1L;
    private static final String BOOK_TITLE_TEST1 = "Lord of the Rings";
    private static final String BOOK_TITLE_TEST2 = "Hamlet";
    private static final long BOOK_ID_TEST2 = 1000L;
    private static final LocalDate BOOK_RELEASEDATE_TEST1 = LocalDate.of(2013, 1, 26);
    private static final LocalDate BOOK_RELEASEDATE_TEST2 = LocalDate.of(2011, 11, 16);
    private static final String BOOK_ISBN_TEST1 = "11-333-12";
    private static final String BOOK_ISBN_TEST2 = "11-666-77";
    private static final String PUBLISHER_NAME_TEST1 = "Planeta";
    private static final String PUBLISHER_NAME_TEST2 = "Gigamesh";
    private static final String PUBLISHER_COUNTRY_TEST1 = "ES";
    private static final String PUBLISHER_COUNTRY_TEST2 = "US";
    private static final String AUTHOR_NAME_TEST1 = "J.R.R.";
    private static final String AUTHOR_NAME_TEST2 = "William";
    private static final String AUTHOR_SURNAME_TEST1 = "Tolkien";
    private static final String AUTHOR_SURNAME_TEST2 = "Shakespeare";
    private static final int ONE_TIME = 1;

    @Mock
    private BookService bookService;

    @Mock
    private BindingResult errors;

    @Mock
    private BookResourceAssembler bookResourceAssembler;

    @InjectMocks
    private BookController controller = new BookController();

    @Before
    public void setUp() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test(expected = InvalidRequestException.class)
    public void testUpdateInvalidRequest() {
        Book bookReq = new BookBuilder()
                .title(BOOK_TITLE_TEST1)
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .build();
        Book bookResp = new Book().copyFrom(bookReq).setId(BOOK_ID_TEST1);
        when(errors.hasErrors()).thenReturn(true);
        when(bookService.save(bookReq)).thenReturn(bookResp);

        controller.updateBook(BOOK_ID_TEST1, bookReq, errors);
    }


    @Test
    public void testCreate() {
        Book bookReq = new BookBuilder()
                .title(BOOK_TITLE_TEST1)
                .authors(Collections.singletonList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
                .build();
        Book bookResp = new Book().copyFrom(bookReq).setId(BOOK_ID_TEST1);
        when(errors.hasErrors()).thenReturn(false);
        when(bookService.create(bookReq)).thenReturn(bookResp);
        when(bookResourceAssembler.toResource(any(Book.class))).thenReturn(createBookResourceWithLink(bookResp));

        ResponseEntity<BookResource> result = controller.createBook(bookReq, errors);

        assertNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertThat(result.getHeaders().getLocation().toString(), containsString("/api/books/" + BOOK_ID_TEST1));
        verify(bookResourceAssembler, times(ONE_TIME)).toResource(bookResp);
        verify(errors, times(ONE_TIME)).hasErrors();
        verify(bookService, times(ONE_TIME)).create(bookReq);
    }

    @Test(expected = InvalidRequestException.class)
    public void testCreateInvalidRequest() {
        Book bookReq = new Book();
        when(errors.hasErrors()).thenReturn(true);

        controller.createBook(bookReq, errors);
    }

    @Test
    public void testUpdate() {
        Book bookReq = new BookBuilder()
                .title(BOOK_TITLE_TEST1)
                .authors(Collections.singletonList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
                .build();
        Book bookDB = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST2)
                .authors(Collections.singletonList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
                .build();
        when(bookService.findOne(BOOK_ID_TEST1)).thenReturn(bookDB);
        when(bookService.save(bookDB.copyFrom(bookReq))).thenReturn(bookDB.copyFrom(bookReq));

        controller.updateBook(BOOK_ID_TEST1, bookReq, errors);

        verify(bookService, times(ONE_TIME)).findOne(BOOK_ID_TEST1);
        verify(bookService, times(ONE_TIME)).save(bookDB.copyFrom(bookReq));
    }

    @Test
    public void testGet() {
        Book bookReq = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Collections.singletonList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
                .build();
        when(bookService.findOne(BOOK_ID_TEST1)).thenReturn(bookReq);
        when(bookResourceAssembler.toResource(any(Book.class))).thenReturn(new BookResource().setBook(bookReq));

        ResponseEntity<BookResource> result = controller.getBook(BOOK_ID_TEST1);

        verify(bookService, times(ONE_TIME)).findOne(BOOK_ID_TEST1);
        verify(bookResourceAssembler, times(ONE_TIME)).toResource(bookReq);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }


    @Test(expected = NotFoundException.class)
    public void testGetNotFound() {
        when(bookService.findOne(BOOK_ID_TEST2)).thenThrow(new NotFoundException("Book not exist"));

        controller.getBook(BOOK_ID_TEST2);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() {
        Book bookReq = new BookBuilder()
                .title(BOOK_TITLE_TEST1)
                .authors(Collections.singletonList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
                .build();
        when(bookService.findOne(BOOK_ID_TEST1)).thenThrow(new NotFoundException("Book not exist"));

        controller.updateBook(BOOK_ID_TEST1, bookReq, errors);
    }

    @Test
    public void testDelete() {
        doNothing().when(bookService).delete(BOOK_ID_TEST1);

        controller.deleteBook(BOOK_ID_TEST1);

        verify(bookService, times(ONE_TIME)).delete(BOOK_ID_TEST1);
    }

    @Test
    public void testFindAll() {
        List<Book> books = createTestBookList();
        when(bookService.findAll()).thenReturn(books);
        when(bookResourceAssembler.toResources(anyListOf(Book.class))).thenReturn(new ArrayList<>());

        ResponseEntity<List<BookResource>> result = controller.getAll();

        verify(bookService, times(ONE_TIME)).findAll();
        verify(bookResourceAssembler, times(ONE_TIME)).toResources(books);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    private BookResource createBookResourceWithLink(Book book) {
        BookResource bookResource = new BookResource().setBook(book);
        bookResource.add(new Link("http://localhost:8080/api/books/" + BOOK_ID_TEST1));
        return bookResource;
    }

    private List<Book> createTestBookList() {
        Book book1 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Collections.singletonList(AuthorTest.createAuthor(AUTHOR_NAME_TEST1, AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(PublisherTest.createPublisher(PUBLISHER_NAME_TEST1, PUBLISHER_COUNTRY_TEST1, false))
                .build();
        Book book2 = new BookBuilder()
                .id(BOOK_ID_TEST2)
                .title(BOOK_TITLE_TEST2)
                .authors(Collections.singletonList(AuthorTest.createAuthor(AUTHOR_NAME_TEST2, AUTHOR_SURNAME_TEST2)))
                .isbn(BOOK_ISBN_TEST2)
                .releaseDate(BOOK_RELEASEDATE_TEST2)
                .publisher(PublisherTest.createPublisher(PUBLISHER_NAME_TEST2, PUBLISHER_COUNTRY_TEST2, true))
                .build();

        return Arrays.asList(book1, book2);
    }
}
