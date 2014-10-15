package com.edwise.completespring.controllers;

import com.edwise.completespring.Application;
import com.edwise.completespring.config.TestContext;
import com.edwise.completespring.entities.Foo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, TestContext.class})
@WebAppConfiguration
@IntegrationTest("db.resetAndLoadOnStartup=false")
public class ITFooControllerTest {
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private static final long FOO_ID_TEST1 = 1l;
    private static final String FOO_TEXT_ATTR_TEST1 = "AttText1";
    private static final LocalDate DATE_TEST1 = new LocalDate(2013, 1, 26);

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
//        Mockito.reset(fooService);
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getAll_FoosFound_ShouldReturnFoundFoos() throws Exception {
        mockMvc.perform(get("/api/foo/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        ;
        // TODO expects con jsonPath
    }

    @Test
    public void getFoo_FooFound_ShouldReturnCorrectFoo() throws Exception {
        mockMvc.perform(get("/api/foo/{id}", (long) FOO_ID_TEST1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        ;
        // TODO expects con jsonPath
    }

    @Test
    public void postFoo_FooCorrect_ShouldReturnCreatedStatus() throws Exception {
        Foo fooToCreate = createFoo(null, FOO_TEXT_ATTR_TEST1, DATE_TEST1);

        mockMvc.perform(post("/api/foo/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(fooToCreate)))
                .andExpect(status().isCreated())
        ;
    }

    // TODO test post failure?

    @Test
    public void putFoo_FooExist_ShouldReturnCreatedStatus() throws Exception {
        Foo fooWithChangedFields = createFoo(null, FOO_TEXT_ATTR_TEST1, DATE_TEST1);

        mockMvc.perform(put("/api/foo/{id}", (long) FOO_ID_TEST1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(fooWithChangedFields))
        )
                .andExpect(status().isNoContent())
        ;
    }

    // TODO test put failure?

    @Test
    public void deleteFoo_FooExist_ShouldReturnNoContentStatus() throws Exception {
        mockMvc.perform(delete("/api/foo/{id}", (long) FOO_ID_TEST1))
                .andExpect(status().isNoContent())
        ;
    }

    private Foo createFoo(Long id, String textAttribute, LocalDate localDateAttribute) {
        return new Foo()
                .setId(id)
                .setSampleTextAttribute(textAttribute)
                .setSampleLocalDateAttribute(localDateAttribute);
    }

    private byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
