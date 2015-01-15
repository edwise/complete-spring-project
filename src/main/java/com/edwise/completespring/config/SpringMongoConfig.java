package com.edwise.completespring.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import java.net.UnknownHostException;

@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {
    @Override
    public String getDatabaseName() {
        return "springBootDB";
    }

    @Override
    @Bean
    public Mongo mongo() throws UnknownHostException {
        return new MongoClient("localhost");
    }
}
