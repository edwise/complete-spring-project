package com.edwise.completespring.controllers;

import com.edwise.completespring.Application;
import com.edwise.completespring.dbutils.DataLoader;
import com.edwise.completespring.entities.Author;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.Publisher;
import com.edwise.completespring.testutil.BookBuilder;
import com.edwise.completespring.testutil.IntegrationTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Collections;

import static com.edwise.completespring.testutil.IsValidFormatDateYMDMatcher.validFormatDateYMD;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * TODO this tests are executed with the same data that is charged only ONCE... maybe is needed to load data with each test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ITBookControllerTest {
    private static final Long BOOK_ID_NOT_EXISTS = 111L;
    private static final String BOOK_TITLE_TEST1 = "Lord of the Rings";
    private static final LocalDate BOOK_RELEASEDATE_TEST1 = LocalDate.of(2013, 1, 26);
    private static final String BOOK_ISBN_TEST1 = "11-333-12";
    private static final String BOOK_ISBN_TEST2 = "11-666-77";
    private static final String PUBLISHER_NAME_TEST1 = "Planeta";
    private static final String PUBLISHER_COUNTRY_TEST1 = "ES";
    private static final String AUTHOR_NAME_TEST1 = "J.R.R.";
    private static final String AUTHOR_NAME_TEST2 = "William";
    private static final String AUTHOR_SURNAME_TEST1 = "Tolkien";
    private static final String BOOK_NOT_FOUND_EXCEPTION_MSG = "Book not found";
    private static final String NOT_EXISTING_USER_USERNAME = "notExistUser";
    private static final String NOT_EXISTING_USER_PASSWORD = "password2";

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void getAll_CorrectUserAndBooksFound_ShouldReturnFoundBooks() throws Exception {
        mockMvc.perform(get("/api/books/")
                .with(httpBasic(DataLoader.USER, DataLoader.PASSWORD_USER))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(1))))
                .andExpect(jsonPath("$[0].book").exists())
                .andExpect(jsonPath("$[0].book.id", is(notNullValue())))
                .andExpect(jsonPath("$[0].book.title", is(notNullValue())))
                .andExpect(jsonPath("$[0].book.authors", is(notNullValue())))
                .andExpect(jsonPath("$[0].book.isbn", is(notNullValue())))
                .andExpect(jsonPath("$[0].book.releaseDate", is(notNullValue())))
                .andExpect(jsonPath("$[0].book.releaseDate", is(validFormatDateYMD())))
                .andExpect(jsonPath("$[0].book.publisher", is(notNullValue())))
                .andExpect(jsonPath("$[0].links", hasSize(1)))
                .andExpect(jsonPath("$[0].links[0].rel", is(notNullValue())))
                .andExpect(jsonPath("$[0].links[0].href", containsString("/api/books/")))
        ;
    }

    @Test
    public void getAll_NotExistingUser_ShouldReturnUnauthorizedCode() throws Exception {
        mockMvc.perform(get("/api/books/").with(httpBasic(NOT_EXISTING_USER_USERNAME, NOT_EXISTING_USER_PASSWORD)))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void getBook_CorrectUserAndBookFound_ShouldReturnCorrectBook() throws Exception {
        mockMvc.perform(get("/api/books/{id}", DataLoader.BOOK_ID_1)
                .with(httpBasic(DataLoader.USER, DataLoader.PASSWORD_USER))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.book").exists())
                .andExpect(jsonPath("$.book.id", is(DataLoader.BOOK_ID_1.intValue())))
                .andExpect(jsonPath("$.book.title", is(notNullValue())))
                .andExpect(jsonPath("$.book.authors", is(notNullValue())))
                .andExpect(jsonPath("$.book.isbn", is(notNullValue())))
                .andExpect(jsonPath("$.book.releaseDate", is(notNullValue())))
                .andExpect(jsonPath("$.book.releaseDate", is(validFormatDateYMD())))
                .andExpect(jsonPath("$.book.publisher", is(notNullValue())))
                .andExpect(jsonPath("$._links").exists())
                .andExpect(jsonPath("$._links.self.href", containsString("/api/books/" + DataLoader.BOOK_ID_1)))
        ;
    }

    @Test
    public void getBook_CorrectUserAndBookNotFound_ShouldReturnNotFoundStatusAndError() throws Exception {
        mockMvc.perform(get("/api/books/{id}", BOOK_ID_NOT_EXISTS)
                .with(httpBasic(DataLoader.USER, DataLoader.PASSWORD_USER))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("id")))
                .andExpect(jsonPath("$.errors[0].message", is(BOOK_NOT_FOUND_EXCEPTION_MSG)))
        ;
    }

    @Test
    public void getBook_NotExistingUser_ShouldReturnUnauthorizedCode() throws Exception {
        mockMvc.perform(get("/api/books/{id}", DataLoader.BOOK_ID_1)
                .with(httpBasic(NOT_EXISTING_USER_USERNAME, NOT_EXISTING_USER_PASSWORD)))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void postBook_CorrectUserAndBookCorrect_ShouldReturnCreatedStatusAndCorrectBook() throws Exception {
        Book bookToCreate = new BookBuilder()
                .title(BOOK_TITLE_TEST1)
                .authors(Collections.singletonList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
                .build();

        mockMvc.perform(post("/api/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(bookToCreate))
                .with(httpBasic(DataLoader.USER, DataLoader.PASSWORD_USER)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/books/")))
        ;
    }

    @Test
    public void postBook_CorrectUserAndBookIncorrect_ShouldReturnBadRequestStatusAndError() throws Exception {
        Book bookToCreate = new BookBuilder()
                .authors(Collections.singletonList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
                .build();

        mockMvc.perform(post("/api/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(bookToCreate))
                .with(httpBasic(DataLoader.USER, DataLoader.PASSWORD_USER))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("title", "isbn", "releaseDate")))
                .andExpect(jsonPath("$.errors[*].message", is(notNullValue())))
        ;
    }

    @Test
    public void postBook_NotExistingUser_ShouldReturnUnauthorizedCode() throws Exception {
        Book bookToCreate = new BookBuilder()
                .title(BOOK_TITLE_TEST1)
                .authors(Collections.singletonList(new Author().setName(AUTHOR_NAME_TEST1).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(false))
                .build();

        mockMvc.perform(post("/api/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(bookToCreate))
                .with(httpBasic(NOT_EXISTING_USER_USERNAME, NOT_EXISTING_USER_PASSWORD)))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void putBook_CorrectUserAndBookExist_ShouldReturnNoContentStatus() throws Exception {
        Book bookToUpdate = new BookBuilder()
                .title(BOOK_TITLE_TEST1)
                .authors(Collections.singletonList(new Author().setName(AUTHOR_NAME_TEST2).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST2)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();

        mockMvc.perform(put("/api/books/{id}", DataLoader.BOOK_ID_2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(bookToUpdate))
                .with(httpBasic(DataLoader.USER, DataLoader.PASSWORD_USER)))
                .andExpect(status().isNoContent())
        ;
    }

    @Test
    public void putBook_CorrectUserAndBookNotExists_ShouldReturnNotFoundStatusAndError() throws Exception {
        Book bookToUpdate = new BookBuilder()
                .title(BOOK_TITLE_TEST1)
                .authors(Collections.singletonList(new Author().setName(AUTHOR_NAME_TEST2).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST2)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();

        mockMvc.perform(put("/api/books/{id}", BOOK_ID_NOT_EXISTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(bookToUpdate))
                .with(httpBasic(DataLoader.USER, DataLoader.PASSWORD_USER))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("id")))
                .andExpect(jsonPath("$.errors[0].message", is(BOOK_NOT_FOUND_EXCEPTION_MSG)))
        ;
    }

    @Test
    public void putBook_CorrectUserAndBookIncorrect_ShouldReturnBadRequestStatusAndError() throws Exception {
        Book bookToUpdate = new BookBuilder()
                .authors(Collections.singletonList(new Author().setName(AUTHOR_NAME_TEST2).setSurname(AUTHOR_SURNAME_TEST1)))
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();

        mockMvc.perform(put("/api/books/{id}", DataLoader.BOOK_ID_2)
                .with(httpBasic(DataLoader.USER, DataLoader.PASSWORD_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(bookToUpdate))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("title", "isbn", "releaseDate")))
                .andExpect(jsonPath("$.errors[*].message", is(notNullValue())))
        ;
    }

    @Test
    public void putBook_NotExistingUser_ShouldReturnUnauthorizedCode() throws Exception {
        Book bookToUpdate = new BookBuilder()
                .title(BOOK_TITLE_TEST1)
                .authors(Collections.singletonList(new Author().setName(AUTHOR_NAME_TEST2).setSurname(AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST2)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(new Publisher().setName(PUBLISHER_NAME_TEST1).setCountry(PUBLISHER_COUNTRY_TEST1).setOnline(true))
                .build();

        mockMvc.perform(put("/api/books/{id}", DataLoader.BOOK_ID_2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(bookToUpdate))
                .with(httpBasic(NOT_EXISTING_USER_USERNAME, NOT_EXISTING_USER_PASSWORD)))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void deleteBook_CorrectUserAndBookExist_ShouldReturnNoContentStatus() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", DataLoader.BOOK_ID_3)
                .with(httpBasic(DataLoader.USER, DataLoader.PASSWORD_USER)))
                .andExpect(status().isNoContent())
        ;
    }

    @Test
    public void deleteBook_NotExistingUser_ShouldReturnUnauthorizedCode() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", DataLoader.BOOK_ID_3)
                .with(httpBasic(NOT_EXISTING_USER_USERNAME, NOT_EXISTING_USER_PASSWORD)))
                .andExpect(status().isUnauthorized())
        ;
    }

}
