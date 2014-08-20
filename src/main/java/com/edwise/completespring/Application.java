package com.edwise.completespring;

import com.edwise.completespring.entities.Author;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.Publisher;
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
    private static final long INITIAL_SEQUENCE = 4;
    private static final long BOOK_ID_1 = 1l;
    private static final long BOOK_ID_2 = 2l;
    private static final long BOOK_ID_3 = 3l;
    private static final long BOOK_ID_4 = 4l;


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
        sequenceRepository.save(new SequenceId()
                                        .setId(BookRepository.BOOK_COLLECTION)
                                        .setSeq(INITIAL_SEQUENCE)
        );

        // save a couple of books
        bookRepository.deleteAll();
        bookRepository.save(new Book(BOOK_ID_1, "Libro prueba mongo", Arrays.asList(new Author().setName("Edu").setSurname("Antón")), "11-333-12",
                new LocalDate(), new Publisher().setName("Editorial 1").setCountry("ES").setOnline(false)));
        bookRepository.save(new Book(BOOK_ID_2, "Libro prueba mongo 2", Arrays.asList(new Author().setName("Otro").setSurname("Más"),
                new Author().setName("S.").setSurname("King")), "12-1234-12", new LocalDate(), new Publisher().setName("Editorial 4")
                    .setCountry("UK").setOnline(true)));
        bookRepository.save(new Book(BOOK_ID_3, "Libro prueba mongo 3", Arrays.asList(new Author().setName("Nadie").setSurname("Nobody")),
                "12-9999-92", new LocalDate(), new Publisher().setName("Editorial 7").setCountry("ES").setOnline(true)));
        bookRepository.save(new Book(BOOK_ID_4, "Libro prueba mongo 4", Arrays.asList(new Author().setName("Perry").setSurname("Mason")),
                "22-34565-12", new LocalDate(), new Publisher().setName("Editorial 33").setCountry("US").setOnline(true)));
    }

}
