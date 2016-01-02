package com.edwise.completespring.controllers;

import com.edwise.completespring.Application;
import com.edwise.completespring.config.SpringSecurityAuthenticationConfig;
import com.edwise.completespring.config.FakeMongoDBContext;
import com.edwise.completespring.entities.UserAccount;
import com.edwise.completespring.entities.UserAccountType;
import com.edwise.completespring.repositories.UserAccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, FakeMongoDBContext.class})
@WebIntegrationTest({"server.port=0", "db.resetAndLoadOnStartup=false"})
public class ITActuatorEndpointsTest {
    private static final String CORRECT_ADMIN_USER_USERNAME = "admin";
    private static final String CORRECT_ADMIN_USER_PASSWORD = "password1234";
    private static final String INCORRECT_ADMIN_USERNAME = "user1";
    private static final String INCORRECT_ADMIN_PASSWORD = "password1";
    private static final String NOT_EXISTING_USER_USERNAME = "notExists";
    private static final String NOT_EXISTING_USER_PASSWORD = "password3456";

    @Mock
    private UserAccountRepository userAccountRepository;

    @Autowired
    private SpringSecurityAuthenticationConfig springSecurityAuthenticationConfig;

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(this.springSecurityAuthenticationConfig, "userAccountRepository", userAccountRepository);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void getInfoActuatorEnpoint_CorrectUser_ShouldReturnOkCode() throws Exception {
        when(userAccountRepository.findByUsername(CORRECT_ADMIN_USER_USERNAME))
                .thenReturn(createUserAccount(1L, CORRECT_ADMIN_USER_USERNAME, CORRECT_ADMIN_USER_PASSWORD,
                        UserAccountType.ADMIN_USER));

        mockMvc.perform(get("/admin/info/").with(httpBasic(CORRECT_ADMIN_USER_USERNAME, CORRECT_ADMIN_USER_PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;
    }

    @Test
    public void getInfoActuatorEnpoint_InCorrectUser_ShouldReturnForbiddenCode() throws Exception {
        when(userAccountRepository.findByUsername(INCORRECT_ADMIN_USERNAME))
                .thenReturn(createUserAccount(1L, INCORRECT_ADMIN_USERNAME, INCORRECT_ADMIN_PASSWORD,
                        UserAccountType.REST_USER));

        mockMvc.perform(get("/admin/info/").with(httpBasic(INCORRECT_ADMIN_USERNAME, INCORRECT_ADMIN_PASSWORD)))
                .andExpect(status().isForbidden())
        ;
    }

    @Test
    public void getInfoActuatorEnpoint_NotExistingUser_ShouldReturnUnauthorizedCode() throws Exception {
        when(userAccountRepository.findByUsername(NOT_EXISTING_USER_USERNAME))
                .thenReturn(null);

        mockMvc.perform(get("/admin/info/").with(httpBasic(NOT_EXISTING_USER_USERNAME, NOT_EXISTING_USER_PASSWORD)))
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
