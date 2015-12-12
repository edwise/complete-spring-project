package com.edwise.completespring.controllers;

import com.edwise.completespring.Application;
import com.edwise.completespring.config.SpringSecurityAuthenticationConfig;
import com.edwise.completespring.config.TestContext;
import com.edwise.completespring.entities.UserAccount;
import com.edwise.completespring.entities.UserAccountType;
import com.edwise.completespring.repositories.UserAccountRepository;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, TestContext.class})
@WebIntegrationTest({"server.port=0", "db.resetAndLoadOnStartup=false"})
public class ITActuatorEndpointsTest {
    private static final String CORRECT_ADMIN_USER_USERNAME = "admin";
    private static final String CORRECT_ADMIN_USER_PASSWORD = "password1234";
    private static final String INCORRECT_ADMIN_USERNAME = "user1";
    private static final String INCORRECT_ADMIN_PASSWORD = "password1";
    private static final String NOT_EXISTING_USER_USERNAME = "notExists";
    private static final String NOT_EXISTING_USER_PASSWORD = "password3456";
    private static String CORRECT_ADMIN_USER_AUTHORIZATION_ENCODED;
    private static String INCORRECT_ADMIN_AUTHORIZATION_ENCODED;
    private static String NOT_EXISTING_USER_AUTHORIZATION_ENCODED;

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
        String authString = CORRECT_ADMIN_USER_USERNAME + ":" + CORRECT_ADMIN_USER_PASSWORD;
        byte[] authEncodecBytes = Base64.encodeBase64(authString.getBytes());
        CORRECT_ADMIN_USER_AUTHORIZATION_ENCODED = new String(authEncodecBytes);

        authString = INCORRECT_ADMIN_USERNAME + ":" + INCORRECT_ADMIN_PASSWORD;
        authEncodecBytes = Base64.encodeBase64(authString.getBytes());
        INCORRECT_ADMIN_AUTHORIZATION_ENCODED = new String(authEncodecBytes);

        authString = NOT_EXISTING_USER_USERNAME + ":" + NOT_EXISTING_USER_PASSWORD;
        authEncodecBytes = Base64.encodeBase64(authString.getBytes());
        NOT_EXISTING_USER_AUTHORIZATION_ENCODED = new String(authEncodecBytes);
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(this.springSecurityAuthenticationConfig, "userAccountRepository", userAccountRepository);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    public void getInfoActuatorEnpoint_CorrectUser_ShouldReturnOkCode() throws Exception {
        when(userAccountRepository.findByUsername(CORRECT_ADMIN_USER_USERNAME))
                .thenReturn(createUserAccount(1L, CORRECT_ADMIN_USER_USERNAME, CORRECT_ADMIN_USER_PASSWORD,
                        UserAccountType.ADMIN_USER));

        mockMvc.perform(get("/admin/info/").header("Authorization", "Basic " + CORRECT_ADMIN_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;
    }

    @Test
    public void getInfoActuatorEnpoint_InCorrectUser_ShouldReturnForbiddenCode() throws Exception {
        when(userAccountRepository.findByUsername(INCORRECT_ADMIN_USERNAME))
                .thenReturn(createUserAccount(1L, INCORRECT_ADMIN_USERNAME, INCORRECT_ADMIN_PASSWORD,
                        UserAccountType.REST_USER));

        mockMvc.perform(get("/admin/info/").header("Authorization", "Basic " + INCORRECT_ADMIN_AUTHORIZATION_ENCODED))
                .andExpect(status().isForbidden())
        ;
    }

    @Test
    public void getInfoActuatorEnpoint_NotExistingUser_ShouldReturnUnauthorizedCode() throws Exception {
        when(userAccountRepository.findByUsername(NOT_EXISTING_USER_USERNAME))
                .thenReturn(null);

        mockMvc.perform(get("/admin/info/").header("Authorization", "Basic " + NOT_EXISTING_USER_AUTHORIZATION_ENCODED))
                .andExpect(status().isUnauthorized())
        ;
    }


    private UserAccount createUserAccount(Long id, String username, String password, UserAccountType userAccountType) {
        return new UserAccount()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setUserType(userAccountType);
    }

}
