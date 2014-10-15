package com.edwise.completespring.controllers;

import com.edwise.completespring.Application;
import com.edwise.completespring.config.TestContext;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.services.BookService;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, TestContext.class})
@WebAppConfiguration
@IntegrationTest("db.resetAndLoadOnStartup=false")
public class ITBookControllerTest {
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

    // TODO more tests
}
