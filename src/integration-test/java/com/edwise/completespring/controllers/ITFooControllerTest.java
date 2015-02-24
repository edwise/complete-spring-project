package com.edwise.completespring.controllers;

import com.edwise.completespring.Application;
import com.edwise.completespring.config.SpringSecurityAuthenticationConfig;
import com.edwise.completespring.config.TestContext;
import com.edwise.completespring.entities.Foo;
import com.edwise.completespring.entities.FooTest;
import com.edwise.completespring.entities.UserAccount;
import com.edwise.completespring.entities.UserAccountType;
import com.edwise.completespring.repositories.UserAccountRepository;
import com.edwise.completespring.testutil.IntegrationTestUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static com.edwise.completespring.testutil.IsValidFormatDateYMDMatcher.validFormatDateYMD;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, TestContext.class})
@WebIntegrationTest({"server.port=0", "db.resetAndLoadOnStartup=false"})
public class ITFooControllerTest {
    private static final long FOO_ID_TEST1 = 1l;
    private static final String ATT_TEXT_1 = "AttText1";
    private static final String FOO_TEXT_ATTR_TEST1 = ATT_TEXT_1;
    private static final LocalDate DATE_TEST1 = LocalDate.of(2013, 1, 26);
    private static final String CORRECT_REST_USER_USERNAME = "user1";
    private static final String CORRECT_REST_USER_PASSWORD = "password1";
    private static final String INCORRECT_USER_USERNAME = "inCorrectUser";
    private static final String INCORRECT_USER_PASSWORD = "password2";
    private static String CORRECT_REST_USER_AUTHORIZATION_ENCODED;
    private static String INCORRECT_USER_AUTHORIZATION_ENCODED;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private SpringSecurityAuthenticationConfig springSecurityAuthenticationConfig;

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @BeforeClass
    public static void setUpCommonStuff() {
        String authString = CORRECT_REST_USER_USERNAME + ":" + CORRECT_REST_USER_PASSWORD;
        byte[] authEncodecBytes = Base64.encodeBase64(authString.getBytes());
        CORRECT_REST_USER_AUTHORIZATION_ENCODED = new String(authEncodecBytes);

        authString = INCORRECT_USER_USERNAME + ":" + INCORRECT_USER_PASSWORD;
        authEncodecBytes = Base64.encodeBase64(authString.getBytes());
        INCORRECT_USER_AUTHORIZATION_ENCODED = new String(authEncodecBytes);
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(this.springSecurityAuthenticationConfig, "userAccountRepository", userAccountRepository);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
        when(userAccountRepository.findByUsername(CORRECT_REST_USER_USERNAME)).thenReturn(createUserAccount());
    }

    @Test
    public void getAll_CorrectUserAndFoosFound_ShouldReturnFoundFoos() throws Exception {
        mockMvc.perform(get("/api/foos/").header("Authorization", "Basic " + CORRECT_REST_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].foo").exists())
                .andExpect(jsonPath("$[0].foo.id", is(1)))
                .andExpect(jsonPath("$[0].foo.sampleTextAttribute", is(ATT_TEXT_1)))
                .andExpect(jsonPath("$[0].foo.sampleLocalDateAttribute", is(notNullValue())))
                .andExpect(jsonPath("$[0].links", hasSize(1)))
                .andExpect(jsonPath("$[0].links[0].rel", is(notNullValue())))
                .andExpect(jsonPath("$[0].links[0].href", containsString("/api/foos/1")))
                .andExpect(jsonPath("$[1].foo").exists())
                .andExpect(jsonPath("$[1].foo.id", is(2)))
                .andExpect(jsonPath("$[1].foo.sampleTextAttribute", is(ATT_TEXT_1)))
                .andExpect(jsonPath("$[1].foo.sampleLocalDateAttribute", is(notNullValue())))
                .andExpect(jsonPath("$[1].links", hasSize(1)))
                .andExpect(jsonPath("$[1].links[0].rel", is(notNullValue())))
                .andExpect(jsonPath("$[1].links[0].href", containsString("/api/foos/2")))
        ;
    }

    @Test
    public void getAll_InCorrectUser_ShouldReturnUnauthorizedCode() throws Exception {
        mockMvc.perform(get("/api/foos/").header("Authorization", "Basic " + INCORRECT_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void getFoo_CorrectUserAndFooFound_ShouldReturnCorrectFoo() throws Exception {
        mockMvc.perform(get("/api/foos/{id}", FOO_ID_TEST1)
                .header("Authorization", "Basic " + CORRECT_REST_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.foo").exists())
                .andExpect(jsonPath("$.foo.id", is(1)))
                .andExpect(jsonPath("$.foo.sampleTextAttribute", is(ATT_TEXT_1)))
                .andExpect(jsonPath("$.foo.sampleLocalDateAttribute", is(notNullValue())))
                .andExpect(jsonPath("$.foo.sampleLocalDateAttribute", is(validFormatDateYMD())))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is(notNullValue())))
                .andExpect(jsonPath("$.links[0].href", containsString("/api/foos/" + FOO_ID_TEST1)))
        ;
    }

    @Test
    public void getFoo_InCorrectUser_ShouldReturnUnauthorizedCode() throws Exception {
        mockMvc.perform(get("/api/foos/{id}", FOO_ID_TEST1)
                .header("Authorization", "Basic " + INCORRECT_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void postFoo_CorrectUserAndFooCorrect_ShouldReturnCreatedStatus() throws Exception {
        Foo fooToCreate = FooTest.createFoo(null, FOO_TEXT_ATTR_TEST1, DATE_TEST1);

        mockMvc.perform(post("/api/foos/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(fooToCreate))
                .header("Authorization", "Basic " + CORRECT_REST_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/foos/" + FOO_ID_TEST1)))
        ;
    }

    @Test
    public void postFoo_CorrectUserAndFooIncorrect_ShouldReturnBadRequestStatusAndError() throws Exception {
        Foo fooToCreate = FooTest.createFoo(null, null, null); // text and date as null

        mockMvc.perform(post("/api/foos/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(fooToCreate))
                .header("Authorization", "Basic " + CORRECT_REST_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("sampleTextAttribute", "sampleLocalDateAttribute")))
                .andExpect(jsonPath("$.errors[*].message", is(notNullValue())))
        ;
    }

    @Test
    public void postFoo_InCorrectUser_ShouldReturnUnauthorizedCode() throws Exception {
        Foo fooToCreate = FooTest.createFoo(null, FOO_TEXT_ATTR_TEST1, DATE_TEST1);

        mockMvc.perform(post("/api/foos/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(fooToCreate))
                .header("Authorization", "Basic " + INCORRECT_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void putFoo_CorrectUserAndFooExist_ShouldReturnCreatedStatus() throws Exception {
        Foo fooWithChangedFields = FooTest.createFoo(null, FOO_TEXT_ATTR_TEST1, DATE_TEST1);

        mockMvc.perform(put("/api/foos/{id}", FOO_ID_TEST1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(fooWithChangedFields))
                .header("Authorization", "Basic " + CORRECT_REST_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isNoContent())
        ;
    }

    @Test
    public void putFoo_CorrectUserAndFooIncorrect_ShouldReturnBadRequestStatusAndError() throws Exception {
        Foo fooWithChangedFields = FooTest.createFoo(null, null, null); // text and date as null

        mockMvc.perform(put("/api/foos/{id}", FOO_ID_TEST1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(fooWithChangedFields))
                .header("Authorization", "Basic " + CORRECT_REST_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("sampleTextAttribute", "sampleLocalDateAttribute")))
                .andExpect(jsonPath("$.errors[*].message", is(notNullValue())))
        ;
    }

    @Test
    public void putFoo_InCorrectUser_ShouldReturnUnauthorizedCode() throws Exception {
        Foo fooWithChangedFields = FooTest.createFoo(null, FOO_TEXT_ATTR_TEST1, DATE_TEST1);

        mockMvc.perform(put("/api/foos/{id}", FOO_ID_TEST1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(fooWithChangedFields))
                .header("Authorization", "Basic " + INCORRECT_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void deleteFoo_CorrectUserAndFooExist_ShouldReturnNoContentStatus() throws Exception {
        mockMvc.perform(delete("/api/foos/{id}", FOO_ID_TEST1)
                .header("Authorization", "Basic " + CORRECT_REST_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isNoContent())
        ;
    }

    @Test
    public void deleteFoo_InCorrectUser_ShouldReturnUnauthorizedCode() throws Exception {
        mockMvc.perform(delete("/api/foos/{id}", FOO_ID_TEST1)
                .header("Authorization", "Basic " + INCORRECT_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isUnauthorized())
        ;
    }

    private UserAccount createUserAccount() {
        return new UserAccount()
                .setId(1L)
                .setUsername(CORRECT_REST_USER_USERNAME)
                .setPassword(CORRECT_REST_USER_PASSWORD)
                .setUserType(UserAccountType.REST_USER);
    }
}
