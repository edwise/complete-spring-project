package com.edwise.completespring.controllers;

import com.edwise.completespring.Application;
import com.edwise.completespring.config.TestContext;
import com.edwise.completespring.entities.Author;
import com.edwise.completespring.entities.AuthorTest;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.Publisher;
import com.edwise.completespring.entities.PublisherTest;
import com.edwise.completespring.exceptions.NotFoundException;
import com.edwise.completespring.services.BookService;
import com.edwise.completespring.testutil.BookBuilder;
import com.edwise.completespring.testutil.IntegrationTestUtil;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, TestContext.class})
@WebAppConfiguration
@IntegrationTest({"server.port=0", "management.port=0", "db.resetAndLoadOnStartup=false"})
public class ITBookControllerTest {
    private static final Long BOOK_ID_TEST1 = 111l;
    private static final String BOOK_TITLE_TEST1 = "Lord of the Rings";
    private static final String BOOK_TITLE_TEST2 = "Hamlet";
    private static final Long BOOK_ID_TEST2 = 1000l;
    private static final LocalDate BOOK_RELEASEDATE_TEST1 = new LocalDate(2013, 1, 26);
    private static final LocalDate BOOK_RELEASEDATE_TEST2 = new LocalDate(2011, 11, 16);
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
    private static final String BOOK_NOT_FOUND_EXCEPTION_MSG = "Book not found";

    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        Mockito.reset(bookService);
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getAll_BooksFound_ShouldReturnFoundBooks() throws Exception {
        when(bookService.findAll()).thenReturn(createTestBookList());

        mockMvc.perform(get("/api/book/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].book").exists())
                .andExpect(jsonPath("$[0].book.id", is(BOOK_ID_TEST1.intValue())))
                .andExpect(jsonPath("$[0]book.title", is(BOOK_TITLE_TEST1)))
                .andExpect(jsonPath("$[0]book.authors", is(notNullValue())))
                .andExpect(jsonPath("$[0]book.isbn", is(BOOK_ISBN_TEST1)))
                .andExpect(jsonPath("$[0]book.releaseDate", is(notNullValue())))
                .andExpect(jsonPath("$[0]book.publisher", is(notNullValue())))
                .andExpect(jsonPath("$[0].links", hasSize(1)))
                .andExpect(jsonPath("$[0].links[0].rel", is(notNullValue())))
                .andExpect(jsonPath("$[0].links[0].href", containsString("/api/book/" + BOOK_ID_TEST1)))
                .andExpect(jsonPath("$[1].book").exists())
                .andExpect(jsonPath("$[1].book.id", is(BOOK_ID_TEST2.intValue())))
                .andExpect(jsonPath("$[1]book.title", is(BOOK_TITLE_TEST2)))
                .andExpect(jsonPath("$[1]book.authors", is(notNullValue())))
                .andExpect(jsonPath("$[1]book.isbn", is(BOOK_ISBN_TEST2)))
                .andExpect(jsonPath("$[1]book.releaseDate", is(notNullValue())))
                .andExpect(jsonPath("$[1]book.publisher", is(notNullValue())))
                .andExpect(jsonPath("$[1].links", hasSize(1)))
                .andExpect(jsonPath("$[1].links[0].rel", is(notNullValue())))
                .andExpect(jsonPath("$[1].links[0].href", containsString("/api/book/" + BOOK_ID_TEST2)))
        ;
        verify(bookService, times(1)).findAll();
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void getAll_BooksNotFound_ShouldReturnEmptyList() throws Exception {
        when(bookService.findAll()).thenReturn(new ArrayList<Book>(0));

        mockMvc.perform(get("/api/book/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)))
        ;
        verify(bookService, times(1)).findAll();
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void getBook_BookFound_ShouldReturnCorrectBook() throws Exception {
        Book bookFound = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
                .build();
        when(bookService.findOne(anyLong())).thenReturn(bookFound);

        mockMvc.perform(get("/api/book/{id}", BOOK_ID_TEST1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.book").exists())
                .andExpect(jsonPath("$.book.id", is(BOOK_ID_TEST1.intValue())))
                .andExpect(jsonPath("$.book.title", is(BOOK_TITLE_TEST1)))
                .andExpect(jsonPath("$.book.authors", is(notNullValue())))
                .andExpect(jsonPath("$.book.isbn", is(BOOK_ISBN_TEST1)))
                .andExpect(jsonPath("$.book.releaseDate", is(notNullValue())))
                .andExpect(jsonPath("$.book.publisher", is(notNullValue())))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is(notNullValue())))
                .andExpect(jsonPath("$.links[0].href", containsString("/api/book/" + BOOK_ID_TEST1)))
        ;
        verify(bookService, times(1)).findOne(BOOK_ID_TEST1);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void getBook_BookNotFound_ShouldReturnNotFoundStatusAndError() throws Exception {
        when(bookService.findOne(anyLong())).thenThrow(new NotFoundException(BOOK_NOT_FOUND_EXCEPTION_MSG));

        mockMvc.perform(get("/api/book/{id}", BOOK_ID_TEST1))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("id")))
                .andExpect(jsonPath("$.errors[0].message", is(BOOK_NOT_FOUND_EXCEPTION_MSG)))
        ;
        verify(bookService, times(1)).findOne(BOOK_ID_TEST1);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void postBook_BookCorrect_ShouldReturnCreatedStatus() throws Exception {
        Book bookToCreate = new BookBuilder()
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
                .build();
        Book bookCreated = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
                .build();
        when(bookService.create(bookToCreate)).thenReturn(bookCreated);

        mockMvc.perform(post("/api/book/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(bookToCreate)))
                .andExpect(status().isCreated())
        ;
        verify(bookService, times(1)).create(bookToCreate);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void postBook_BookIncorrect_ShouldReturnBadRequestStatusAndError() throws Exception {
        Book bookToCreate = new BookBuilder()
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
                .build();

        mockMvc.perform(post("/api/book/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(bookToCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("title", "isbn", "releaseDate")))
                .andExpect(jsonPath("$.errors[*].message", is(notNullValue())))
        ;
        verify(bookService, never()).create(any(Book.class));
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void putBook_BookExist_ShouldReturnCreatedStatus() throws Exception {
        Book bookToUpdate = new BookBuilder()
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST2).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST2)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();
        Book bookOld = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
                .build();
        Book bookCopiedIn = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST2).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST2)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();
        when(bookService.findOne(anyLong())).thenReturn(bookOld);
        when(bookService.save(any(Book.class))).thenReturn(bookCopiedIn);

        mockMvc.perform(put("/api/book/{id}", BOOK_ID_TEST1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(bookToUpdate)))
                .andExpect(status().isNoContent())
        ;
        verify(bookService, times(1)).findOne(BOOK_ID_TEST1);
        verify(bookService, times(1)).save(bookCopiedIn);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void putBook_BookNotExists_ShouldReturnNotFoundStatusAndError() throws Exception {
        Book bookToUpdate = new BookBuilder()
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST2).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST2)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();
        when(bookService.findOne(anyLong())).thenThrow(new NotFoundException(BOOK_NOT_FOUND_EXCEPTION_MSG));

        mockMvc.perform(put("/api/book/{id}", BOOK_ID_TEST1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(bookToUpdate)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("id")))
                .andExpect(jsonPath("$.errors[0].message", is(BOOK_NOT_FOUND_EXCEPTION_MSG)))
        ;
        verify(bookService, times(1)).findOne(BOOK_ID_TEST1);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void putBook_BookIncorrect_ShouldReturnBadRequestStatusAndError() throws Exception {
        Book bookToUpdate = new BookBuilder()
                .authors(Arrays.asList(new Author().setName(AUTHOR_NAME_TEST2).setSurname(AUTHOR_SURNAME_TEST1)))
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();

        mockMvc.perform(put("/api/book/{id}", BOOK_ID_TEST1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(bookToUpdate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("title", "isbn", "releaseDate")))
                .andExpect(jsonPath("$.errors[*].message", is(notNullValue())))
        ;
        verify(bookService, never()).findOne(anyLong());
        verify(bookService, never()).save(any(Book.class));
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void deleteBook_BookExist_ShouldReturnNoContentStatus() throws Exception {
        mockMvc.perform(delete("/api/book/{id}", BOOK_ID_TEST1))
                .andExpect(status().isNoContent())
        ;
        verify(bookService, times(1)).delete(BOOK_ID_TEST1);
        verifyNoMoreInteractions(bookService);
    }

    private List<Book> createTestBookList() {
        Book book1 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Arrays.asList(AuthorTest.createAuthor(AUTHOR_NAME_TEST1, AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(PublisherTest.createPublisher(PUBLISHER_NAME_TEST1, PUBLISHER_COUNTRY_TEST1, false))
                .build();
        Book book2 = new BookBuilder()
                .id(BOOK_ID_TEST2)
                .title(BOOK_TITLE_TEST2)
                .authors(Arrays.asList(AuthorTest.createAuthor(AUTHOR_NAME_TEST2, AUTHOR_SURNAME_TEST2)))
                .isbn(BOOK_ISBN_TEST2)
                .releaseDate(BOOK_RELEASEDATE_TEST2)
                .publisher(PublisherTest.createPublisher(PUBLISHER_NAME_TEST2, PUBLISHER_COUNTRY_TEST2, true))
                .build();

        return Arrays.asList(book1, book2);
    }
}
