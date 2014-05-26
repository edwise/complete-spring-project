package com.edwise.completespring;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ApplicationForServerTest {

    private ApplicationForServer applicationForServer;

    @Mock
    private SpringApplicationBuilder springApplicationBuilder;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        applicationForServer = new ApplicationForServer();
    }

    @Test
    public void testConfigure() throws Exception {
        applicationForServer.configure(springApplicationBuilder);
        verify(springApplicationBuilder, times(1)).sources(Application.class);
    }
}