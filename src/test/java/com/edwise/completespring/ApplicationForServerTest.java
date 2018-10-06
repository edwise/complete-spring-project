package com.edwise.completespring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationForServerTest {

    @Test
    public void testConfigureForServer() {
        SpringApplicationBuilder springApplicationBuilder = mock(SpringApplicationBuilder.class);
        ApplicationForServer applicationForServer = new ApplicationForServer();

        applicationForServer.configure(springApplicationBuilder);

        verify(springApplicationBuilder).sources(Application.class);
    }
}