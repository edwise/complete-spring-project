package com.edwise.completespring.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * Created by user EAnton on 07/04/2014.
 */
@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {
    @Override
    public String getDatabaseName() {
        return "springBootDB";
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient("localhost");
    }
}
