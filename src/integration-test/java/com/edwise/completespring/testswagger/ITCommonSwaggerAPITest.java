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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebIntegrationTest({"server.port=0", "db.resetAndLoadOnStartup=false"})
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
        mockMvc.perform(get("/api-docs/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.apis", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.apis[*].path", containsInAnyOrder("/default/books", "/default/foos")))
                .andExpect(jsonPath("$.apis[*].description", containsInAnyOrder("Books API", "Foo API")))
                .andExpect(jsonPath("$.apis", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info.title", is("Books API")))
                .andExpect(jsonPath("$.info.description", is("Your book database!")))
                .andExpect(jsonPath("$.info.termsOfServiceUrl", is("http://en.wikipedia.org/wiki/Terms_of_service")))
                .andExpect(jsonPath("$.info.contact", is("edwise.null@gmail.com")))
                .andExpect(jsonPath("$.info.license", is("Apache 2.0")))
                .andExpect(jsonPath("$.info.licenseUrl", is("http://www.apache.org/licenses/LICENSE-2.0.html")))

        ;
    }
}
