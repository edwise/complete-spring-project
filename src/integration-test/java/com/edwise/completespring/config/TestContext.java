package com.edwise.completespring.config;

import com.edwise.completespring.services.BookService;
import com.edwise.completespring.services.impl.BookServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestContext {

    @Bean
    public BookService bookService() {
        return Mockito.mock(BookServiceImpl.class);
    }
}
