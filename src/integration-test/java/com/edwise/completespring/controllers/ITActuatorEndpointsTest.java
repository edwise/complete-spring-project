package com.edwise.completespring.controllers;

import com.edwise.completespring.Application;
import com.edwise.completespring.dbutils.DataLoader;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ITActuatorEndpointsTest {
    private static final String NOT_EXISTING_USER_USERNAME = "notExists";
    private static final String NOT_EXISTING_USER_PASSWORD = "password3456";

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
    public void getInfoActuatorEnpoint_CorrectUser_ShouldReturnOkCode() throws Exception {
        mockMvc.perform(get("/actuator/info/")
                .with(httpBasic(DataLoader.ADMIN, DataLoader.PASSWORD_ADMIN))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        ;
    }

    @Test
    public void getInfoActuatorEnpoint_InCorrectUser_ShouldReturnForbiddenCode() throws Exception {
        mockMvc.perform(get("/actuator/info/")
                .with(httpBasic(DataLoader.USER, DataLoader.PASSWORD_USER))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
        ;
    }

    @Test
    public void getInfoActuatorEnpoint_NotExistingUser_ShouldReturnUnauthorizedCode() throws Exception {
        mockMvc.perform(get("/actuator/info/")
                .with(httpBasic(NOT_EXISTING_USER_USERNAME, NOT_EXISTING_USER_PASSWORD))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
        ;
    }

}
