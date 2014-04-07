package com.edwise.completespring;

import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.SequenceId;
import com.edwise.completespring.repositories.BookRepository;
import com.edwise.completespring.repositories.SequenceIdRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

/**
 * Spring Boot Application class
 */
@ComponentScan
@EnableAutoConfiguration
public class Application implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SequenceIdRepository sequenceRepository;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fillDBData();
    }

    private void fillDBData() {
        // create the sequence
        sequenceRepository.save(new SequenceId(BookRepository.BOOK_COLLECTION, 4));

        // save a couple of books
        bookRepository.deleteAll();
        bookRepository.save(new Book(1, "Libro prueba mongo", Arrays.asList("Edu"), "11-333-12", new LocalDate()));
        bookRepository.save(new Book(2, "Libro prueba mongo 2", Arrays.asList("Otro", "S. King"), "12-1234-12", new LocalDate()));
        bookRepository.save(new Book(3, "Libro prueba mongo 3", Arrays.asList("Nadie"), "12-9999-92", new LocalDate()));
    }

}
