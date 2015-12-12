package com.edwise.completespring.config;

import com.edwise.completespring.repositories.BookRepository;
import com.edwise.completespring.repositories.SequenceIdRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@Configuration
public class TestContext {

    @Primary
    @Bean
    public BookRepository bookRepositoryTest() {
        return mock(BookRepository.class);
    }

    @Primary
    @Bean
    public SequenceIdRepository sequenceIdRepositoryTest() {
        return mock(SequenceIdRepository.class);
    }
}
