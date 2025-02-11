package com.edwise.completespring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ApplicationForServerTest {

    @Test
    public void testConfigureForServer() {
        SpringApplicationBuilder springApplicationBuilder = mock(SpringApplicationBuilder.class);
        ApplicationForServer applicationForServer = new ApplicationForServer();

        applicationForServer.configure(springApplicationBuilder);

        verify(springApplicationBuilder).sources(Application.class);
    }
}