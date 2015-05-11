package com.edwise.completespring.config;

import com.edwise.completespring.repositories.BookRepository;
import com.edwise.completespring.repositories.SequenceIdRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestContext {

    @Primary
    @Bean
    public BookRepository bookRepositoryTest() {
        return Mockito.mock(BookRepository.class);
    }

    @Primary
    @Bean
    public SequenceIdRepository sequenceIdRepositoryTest() {
        return Mockito.mock(SequenceIdRepository.class);
    }
}
