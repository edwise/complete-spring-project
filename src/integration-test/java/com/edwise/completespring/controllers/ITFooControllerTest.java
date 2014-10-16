package com.edwise.completespring.controllers;

import com.edwise.completespring.Application;
import com.edwise.completespring.config.TestContext;
import com.edwise.completespring.entities.Foo;
import com.edwise.completespring.entities.FooTest;
import com.edwise.completespring.testutil.IntegrationTestUtil;
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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
public class ITFooControllerTest {
    private static final long FOO_ID_TEST1 = 1l;
    public static final String ATT_TEXT_1 = "AttText1";
    private static final String FOO_TEXT_ATTR_TEST1 = ATT_TEXT_1;
    private static final LocalDate DATE_TEST1 = new LocalDate(2013, 1, 26);

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getAll_FoosFound_ShouldReturnFoundFoos() throws Exception {
        mockMvc.perform(get("/api/foo/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].foo").exists())
                .andExpect(jsonPath("$[0].foo.id", is(1)))
                .andExpect(jsonPath("$[0].foo.sampleTextAttribute", is(ATT_TEXT_1)))
                .andExpect(jsonPath("$[0].foo.sampleLocalDateAttribute", is(notNullValue())))
                .andExpect(jsonPath("$[0].links", hasSize(1)))
                .andExpect(jsonPath("$[0].links[0].rel", is(notNullValue())))
                .andExpect(jsonPath("$[0].links[0].href", containsString("/api/foo/1")))
                .andExpect(jsonPath("$[1].foo").exists())
                .andExpect(jsonPath("$[1].foo.id", is(2)))
                .andExpect(jsonPath("$[1].foo.sampleTextAttribute", is(ATT_TEXT_1)))
                .andExpect(jsonPath("$[1].foo.sampleLocalDateAttribute", is(notNullValue())))
                .andExpect(jsonPath("$[1].links", hasSize(1)))
                .andExpect(jsonPath("$[1].links[0].rel", is(notNullValue())))
                .andExpect(jsonPath("$[1].links[0].href", containsString("/api/foo/2")))
        ;
    }

    @Test
    public void getFoo_FooFound_ShouldReturnCorrectFoo() throws Exception {
        mockMvc.perform(get("/api/foo/{id}", FOO_ID_TEST1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.foo").exists())
                .andExpect(jsonPath("$.foo.id", is(1)))
                .andExpect(jsonPath("$.foo.sampleTextAttribute", is(ATT_TEXT_1)))
                .andExpect(jsonPath("$.foo.sampleLocalDateAttribute", is(notNullValue())))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is(notNullValue())))
                .andExpect(jsonPath("$.links[0].href", containsString("/api/foo/" + FOO_ID_TEST1)))
        ;
    }

    @Test
    public void postFoo_FooCorrect_ShouldReturnCreatedStatus() throws Exception {
        Foo fooToCreate = FooTest.createFoo(null, FOO_TEXT_ATTR_TEST1, DATE_TEST1);

        mockMvc.perform(post("/api/foo/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(fooToCreate)))
                .andExpect(status().isCreated())
        ;
    }

    @Test
    public void postFoo_FooIncorrect_ShouldReturnBadRequestStatusAndError() throws Exception {
        Foo fooToCreate = FooTest.createFoo(null, null, null); // text and date as null

        mockMvc.perform(post("/api/foo/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(fooToCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("sampleTextAttribute", "sampleLocalDateAttribute")))
                .andExpect(jsonPath("$.errors[*].message", is(notNullValue())))
        ;
    }

    @Test
    public void putFoo_FooExist_ShouldReturnCreatedStatus() throws Exception {
        Foo fooWithChangedFields = FooTest.createFoo(null, FOO_TEXT_ATTR_TEST1, DATE_TEST1);

        mockMvc.perform(put("/api/foo/{id}", FOO_ID_TEST1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(fooWithChangedFields)))
                .andExpect(status().isNoContent())
        ;
    }

    @Test
    public void putFoo_FooIncorrect_ShouldReturnBadRequestStatusAndError() throws Exception {
        Foo fooToCreate = FooTest.createFoo(null, null, null); // text and date as null

        mockMvc.perform(put("/api/foo/{id}", FOO_ID_TEST1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(fooToCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("sampleTextAttribute", "sampleLocalDateAttribute")))
                .andExpect(jsonPath("$.errors[*].message", is(notNullValue())))
        ;
    }

    @Test
    public void deleteFoo_FooExist_ShouldReturnNoContentStatus() throws Exception {
        mockMvc.perform(delete("/api/foo/{id}", FOO_ID_TEST1))
                .andExpect(status().isNoContent())
        ;
    }
}
