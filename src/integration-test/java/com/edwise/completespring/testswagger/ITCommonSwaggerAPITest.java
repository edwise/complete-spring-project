package com.edwise.completespring.testswagger;

import com.edwise.completespring.Application;
import com.edwise.completespring.config.SwaggerConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ITCommonSwaggerAPITest {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getApiDocsSwagger_shouldReturnGeneralInfoOfAPI() throws Exception {
        mockMvc.perform(get("/v2/api-docs").param("group", SwaggerConfig.BOOKS_API_GROUP))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info.title", is("Books API")))
                .andExpect(jsonPath("$.info.description", is("Your book database!")))
                .andExpect(jsonPath("$.info.termsOfService", is("http://en.wikipedia.org/wiki/Terms_of_service")))
                .andExpect(jsonPath("$.info.contact.email", is("edwise.null@gmail.com")))
                .andExpect(jsonPath("$.info.license.name", is("Apache License Version 2.0")))
                .andExpect(jsonPath("$.info.license.url", is("http://www.apache.org/licenses/LICENSE-2.0.html")))

        ;
    }

    @Test
    public void getApiBooksDocsSwagger_shouldReturnBooksInfoOfAPI() throws Exception {
        mockMvc.perform(get("/v2/api-docs").param("group", SwaggerConfig.BOOKS_API_GROUP))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.paths").exists())
                .andExpect(jsonPath("$.paths./api/books/").exists())
        ;
    }

    @Test
    public void getApiFoosDocsSwagger_shouldReturnFoosInfoOfAPI() throws Exception {
        mockMvc.perform(get("/v2/api-docs").param("group", SwaggerConfig.BOOKS_API_GROUP))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.paths").exists())
                .andExpect(jsonPath("$.paths./api/foos/").exists())
        ;
    }
}
