package com.edwise.completespring.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import java.net.UnknownHostException;

@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {

    @Value("${db.name}")
    private String databaseName;

    @Value("${db.host}")
    private String databaseHost;

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    @Bean
    public Mongo mongo() throws UnknownHostException {
        return new MongoClient(databaseHost);
    }

}
