package com.edwise.completespring;

import com.edwise.completespring.dbutils.DataLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot Application class
 */
@Slf4j
@SpringBootApplication
public class Application {

    @Autowired
    private DataLoader dataLoader;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(@Value("${db.resetAndLoadOnStartup:true}") boolean resetAndLoadDBDataOnStartup) {
        return args -> initApp(resetAndLoadDBDataOnStartup);
    }

    void initApp(boolean restAndLoadDBData) {
        log.info("Init Application...");
        if (restAndLoadDBData) {
            dataLoader.fillDBData();
        }
        log.info("Aplication initiated!");
    }

}
