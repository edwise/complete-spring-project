package com.edwise.completespring.controllers;

import com.edwise.completespring.Application;
import com.edwise.completespring.config.TestContext;
import com.edwise.completespring.entities.Author;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.Publisher;
import com.edwise.completespring.exceptions.NotFoundException;
import com.edwise.completespring.services.BookService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
        when(bookService.findAll()).thenReturn(new ArrayList<Book>()); // TODO book list...

        mockMvc.perform(get("/api/book/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;
        // TODO expects con jsonPath
        verify(bookService, times(1)).findAll();
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void getAll_BooksNotFound_ShouldReturnEmptyList() throws Exception {
        when(bookService.findAll()).thenReturn(new ArrayList<Book>());

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
        // TODO use builder book
        Book bookFound = new Book()
                .setId(BOOK_ID_TEST1)
                .setTitle(BOOK_TITLE_TEST1)
                .setAuthors(Arrays.asList(new Author().setName("Edu").setSurname("Antón")))
                .setIsbn(BOOK_ISBN_TEST1)
                .setReleaseDate(new LocalDate())
                .setPublisher(new Publisher().setName("Editorial 1").setCountry("ES").setOnline(false));
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
        // TODO use builder book
        Book bookToCreate = new Book()
                .setTitle(BOOK_TITLE_TEST1)
                .setAuthors(Arrays.asList(new Author().setName("Edu").setSurname("Antón")))
                .setIsbn(BOOK_ISBN_TEST1)
                .setReleaseDate(new LocalDate())
                .setPublisher(new Publisher().setName("Editorial 1").setCountry("ES").setOnline(false));
        Book bookCreated = new Book()
                .setId(BOOK_ID_TEST1)
                .setTitle(BOOK_TITLE_TEST1)
                .setAuthors(Arrays.asList(new Author().setName("Edu").setSurname("Antón")))
                .setIsbn(BOOK_ISBN_TEST1)
                .setReleaseDate(new LocalDate())
                .setPublisher(new Publisher().setName("Editorial 1").setCountry("ES").setOnline(false));
        when(bookService.create(bookToCreate)).thenReturn(bookCreated);

        mockMvc.perform(post("/api/book/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(bookToCreate)))
                .andExpect(status().isCreated())
        ;
        verify(bookService, times(1)).create(bookToCreate);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void postBook_BookIncorrect_ShouldReturnBadRequestStatusAndError() throws Exception {
        // TODO use builder book
        Book bookToCreate = new Book()
                .setAuthors(Arrays.asList(new Author().setName("Edu").setSurname("Antón")))
                .setPublisher(new Publisher().setName("Editorial 1").setCountry("ES").setOnline(false)); // title, isbn and date as null

        mockMvc.perform(post("/api/book/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(bookToCreate)))
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
        // TODO use builder book
        Book bookToUpdate = new Book()
                .setTitle(BOOK_TITLE_TEST1)
                .setAuthors(Arrays.asList(new Author().setName("EduNew").setSurname("Antón")))
                .setIsbn(BOOK_ISBN_TEST2)
                .setReleaseDate(new LocalDate())
                .setPublisher(new Publisher().setName("Editorial 1").setCountry("ES").setOnline(true));
        Book bookOld = new Book()
                .setId(BOOK_ID_TEST1)
                .setTitle(BOOK_TITLE_TEST1)
                .setAuthors(Arrays.asList(new Author().setName("Edu").setSurname("Antón")))
                .setIsbn(BOOK_ISBN_TEST1)
                .setReleaseDate(new LocalDate())
                .setPublisher(new Publisher().setName("Editorial 1").setCountry("ES").setOnline(false));
        Book bookCopiedIn = new Book()
                .setId(BOOK_ID_TEST1)
                .setTitle(BOOK_TITLE_TEST1)
                .setAuthors(Arrays.asList(new Author().setName("EduNew").setSurname("Antón")))
                .setIsbn(BOOK_ISBN_TEST2)
                .setReleaseDate(new LocalDate())
                .setPublisher(new Publisher().setName("Editorial 1").setCountry("ES").setOnline(true));
        when(bookService.findOne(anyLong())).thenReturn(bookOld);
        when(bookService.save(any(Book.class))).thenReturn(bookCopiedIn);

        mockMvc.perform(put("/api/book/{id}", BOOK_ID_TEST1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(bookToUpdate)))
                .andExpect(status().isNoContent())
        ;
        verify(bookService, times(1)).findOne(BOOK_ID_TEST1);
        verify(bookService, times(1)).save(bookCopiedIn);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    public void putBook_BookNotExists_ShouldReturnNotFoundStatusAndError() throws Exception {
        Book bookToUpdate = new Book()
                .setTitle(BOOK_TITLE_TEST1)
                .setAuthors(Arrays.asList(new Author().setName("EduNew").setSurname("Antón")))
                .setIsbn(BOOK_ISBN_TEST2)
                .setReleaseDate(new LocalDate())
                .setPublisher(new Publisher().setName("Editorial 1").setCountry("ES").setOnline(true));
        when(bookService.findOne(anyLong())).thenThrow(new NotFoundException(BOOK_NOT_FOUND_EXCEPTION_MSG));

        mockMvc.perform(put("/api/book/{id}", BOOK_ID_TEST1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(bookToUpdate)))
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
        Book bookToUpdate = new Book()
                .setAuthors(Arrays.asList(new Author().setName("EduNew").setSurname("Antón")))
                .setPublisher(new Publisher().setName("Editorial 1").setCountry("ES").setOnline(true)); // title, isbn and date as null

        mockMvc.perform(put("/api/book/{id}", BOOK_ID_TEST1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(bookToUpdate)))
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

    // TODO sacar esto a una clase util??
    private byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
