package com.edwise.completespring.testswagger;

import com.edwise.completespring.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebIntegrationTest({"server.port=0", "db.resetAndLoadOnStartup=false"})
public class ITBookSwaggerAPITest {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getApiBooksDocsSwagger_shouldReturnBooksInfoOfAPI() throws Exception {
        mockMvc.perform(get("/api-docs/default/books/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.resourcePath", is("/api/books")))
                .andExpect(jsonPath("$.apis", hasSize(5)))
                .andExpect(jsonPath("$.models").exists())
                .andExpect(jsonPath("$.models.BookResource").exists())
                .andExpect(jsonPath("$.models.Book entity").exists())
                .andExpect(jsonPath("$.models.Publisher entity").exists())
                .andExpect(jsonPath("$.models.Author entity").exists())
        ;
    }
}
