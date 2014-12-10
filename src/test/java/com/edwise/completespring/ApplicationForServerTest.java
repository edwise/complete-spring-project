package com.edwise.completespring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationForServerTest {

    private static final int ONE_TIME = 1;

    @Mock
    private SpringApplicationBuilder springApplicationBuilder;

    private ApplicationForServer applicationForServer;

    @Before
    public void setUp() {
        applicationForServer = new ApplicationForServer();
    }

    @Test
    public void testConfigure() throws Exception {
        applicationForServer.configure(springApplicationBuilder);

        verify(springApplicationBuilder, times(ONE_TIME)).sources(Application.class);
    }
}