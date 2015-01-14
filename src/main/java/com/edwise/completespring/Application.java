package com.edwise.completespring;

import com.edwise.completespring.entities.Author;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.Publisher;
import com.edwise.completespring.entities.SequenceId;
import com.edwise.completespring.entities.UserAccount;
import com.edwise.completespring.repositories.BookRepository;
import com.edwise.completespring.repositories.SequenceIdRepository;
import com.edwise.completespring.repositories.UserAccountRepository;
import com.edwise.completespring.services.impl.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class Application implements CommandLineRunner {
    private static final long BOOKS_INITIAL_SEQUENCE = 4;
    private static final long USERACCOUNTS_INITIAL_SEQUENCE = 2;
    private static final long BOOK_ID_1 = 1L;
    private static final long BOOK_ID_2 = 2L;
    private static final long BOOK_ID_3 = 3L;
    private static final long BOOK_ID_4 = 4L;

    public static final String USERACCOUNTS_COLLECTION = "users";

    @Value("${db.resetAndLoadOnStartup:true}")
    private boolean resetAndLoadOnStartup;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private SequenceIdRepository sequenceRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Init Application...");

        if (resetAndLoadOnStartup) {
            fillDBData();
        }

        log.info("Aplication initiated!");
    }

    private void fillDBData() {
        fillDBBooksData();
        fillDBUsersData();
        log.info("DB initiated with data.");
    }

    private void fillDBUsersData() {
        sequenceRepository.save(new SequenceId()
                        .setId(USERACCOUNTS_COLLECTION)
                        .setSeq(USERACCOUNTS_INITIAL_SEQUENCE)
        );

        userAccountRepository.deleteAll();

        userAccountRepository.save(new UserAccount()
                .setId(1L)
                .setUsername("restBookUser")
                .setPassword("password1"));

        userAccountRepository.save(new UserAccount()
                .setId(2L)
                .setUsername("adminUser")
                .setPassword("password2"));
    }

    private void fillDBBooksData() {
        sequenceRepository.save(new SequenceId()
                        .setId(BookServiceImpl.BOOK_COLLECTION)
                        .setSeq(BOOKS_INITIAL_SEQUENCE)
        );

        bookRepository.deleteAll();

        bookRepository.save(new Book()
                .setId(BOOK_ID_1)
                .setTitle("Libro prueba mongo")
                .setAuthors(Arrays.asList(new Author().setName("Edu").setSurname("Antón")))
                .setIsbn("11-333-12")
                .setReleaseDate(new LocalDate())
                .setPublisher(new Publisher().setName("Editorial 1").setCountry("ES").setOnline(false)));

        bookRepository.save(new Book()
                .setId(BOOK_ID_2)
                .setTitle("Libro prueba mongo 2")
                .setAuthors(Arrays.asList(new Author().setName("Otro").setSurname("Más"), new Author().setName("S.").setSurname("King")))
                .setIsbn("12-1234-12")
                .setReleaseDate(new LocalDate())
                .setPublisher(new Publisher().setName("Editorial 4").setCountry("UK").setOnline(true)));

        bookRepository.save(new Book()
                .setId(BOOK_ID_3)
                .setTitle("Libro prueba mongo 3")
                .setAuthors(Arrays.asList(new Author().setName("Nadie").setSurname("Nobody")))
                .setIsbn("12-9999-92")
                .setReleaseDate(new LocalDate())
                .setPublisher(new Publisher().setName("Editorial 7").setCountry("ES").setOnline(true)));

        bookRepository.save(new Book()
                .setId(BOOK_ID_4)
                .setTitle("Libro prueba mongo 4")
                .setAuthors(Arrays.asList(new Author().setName("Perry").setSurname("Mason")))
                .setIsbn("22-34565-12")
                .setReleaseDate(new LocalDate())
                .setPublisher(new Publisher().setName("Editorial 33").setCountry("US").setOnline(true)));
    }

}
