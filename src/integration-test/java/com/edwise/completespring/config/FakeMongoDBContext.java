package com.edwise.completespring.config;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class FakeMongoDBContext {

    @Primary
    @Bean
    public Mongo mongo() {
        return new Fongo("InMemoryMongo").getMongo();
    }

}
