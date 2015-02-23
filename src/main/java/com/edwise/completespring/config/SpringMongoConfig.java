package com.edwise.completespring.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Arrays;

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

    @Override
    public CustomConversions customConversions() {
        return new CustomConversions(Arrays.asList(
                new LocalDateToStringConverter(),
                new StringToLocalDateConverter()));
    }

    class LocalDateToStringConverter implements Converter<LocalDate, String> {
        @Override
        public String convert(LocalDate localDate) {
            return localDate.toString();
        }
    }

    class StringToLocalDateConverter implements Converter<String, LocalDate> {
        @Override
        public LocalDate convert(String source) {
            return LocalDate.parse(source);
        }
    }
}
