package com.edwise.completespring;

import com.edwise.completespring.entities.Author;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.Publisher;
import com.edwise.completespring.entities.SequenceId;
import com.edwise.completespring.entities.UserAccount;
import com.edwise.completespring.entities.UserAccountType;
import com.edwise.completespring.repositories.BookRepository;
import com.edwise.completespring.repositories.SequenceIdRepository;
import com.edwise.completespring.repositories.UserAccountRepository;
import com.edwise.completespring.services.impl.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Spring Boot Application class
 */
@Slf4j
@SpringBootApplication
public class Application {
    private static final long BOOKS_INITIAL_SEQUENCE = 4;
    private static final long USERACCOUNTS_INITIAL_SEQUENCE = 3;
    private static final long BOOK_ID_1 = 1L;
    private static final long BOOK_ID_2 = 2L;
    private static final long BOOK_ID_3 = 3L;
    private static final long BOOK_ID_4 = 4L;
    private static final long USER_ID_1 = 1L;
    private static final long USER_ID_2 = 2L;
    private static final String USERACCOUNTS_COLLECTION = "users";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(@Value("${db.resetAndLoadOnStartup:true}") boolean resetAndLoadOnStartup,
                           SequenceIdRepository sequenceRepository,
                           UserAccountRepository userAccountRepository,
                           BookRepository bookRepository) {
        return args -> {
            log.info("Init Application...");
            if (resetAndLoadOnStartup) {
                fillDBData(sequenceRepository, userAccountRepository, bookRepository);
            }
            log.info("Aplication initiated!");
        };
    }

    void fillDBData(SequenceIdRepository sequenceRepository, UserAccountRepository userAccountRepository,
                    BookRepository bookRepository) {
        fillDBUsersData(sequenceRepository, userAccountRepository);
        fillDBBooksData(sequenceRepository, bookRepository);
        log.info("DB initiated with data.");
    }

    private void fillDBUsersData(SequenceIdRepository sequenceRepository, UserAccountRepository userAccountRepository) {
        sequenceRepository.save(new SequenceId()
                        .setId(USERACCOUNTS_COLLECTION)
                        .setSeq(USERACCOUNTS_INITIAL_SEQUENCE)
        );
        userAccountRepository.deleteAll();
        Arrays.asList(
                new UserAccount()
                        .setId(USER_ID_1)
                        .setUsername("user1")
                        .setPassword("password1")
                        .setUserType(UserAccountType.REST_USER),
                new UserAccount()
                        .setId(USER_ID_2)
                        .setUsername("admin")
                        .setPassword("password1234")
                        .setUserType(UserAccountType.ADMIN_USER))
                .forEach(userAccountRepository::save);
    }

    private void fillDBBooksData(SequenceIdRepository sequenceRepository, BookRepository bookRepository) {
        sequenceRepository.save(new SequenceId()
                        .setId(BookServiceImpl.BOOK_COLLECTION)
                        .setSeq(BOOKS_INITIAL_SEQUENCE)
        );
        bookRepository.deleteAll();
        Arrays.asList(
                new Book()
                        .setId(BOOK_ID_1)
                        .setTitle("Libro prueba mongo")
                        .setAuthors(Arrays.asList(new Author().setName("Edu").setSurname("Antón")))
                        .setIsbn("11-333-12")
                        .setReleaseDate(LocalDate.now())
                        .setPublisher(new Publisher().setName("Editorial 1").setCountry("ES").setOnline(false)),
                new Book()
                        .setId(BOOK_ID_2)
                        .setTitle("Libro prueba mongo 2")
                        .setAuthors(
                                Arrays.asList(
                                        new Author().setName("Otro").setSurname("Más"),
                                        new Author().setName("S.").setSurname("King")))
                        .setIsbn("12-1234-12")
                        .setReleaseDate(LocalDate.now())
                        .setPublisher(new Publisher().setName("Editorial 4").setCountry("UK").setOnline(true)),
                new Book()
                        .setId(BOOK_ID_3)
                        .setTitle("Libro prueba mongo 3")
                        .setAuthors(Arrays.asList(new Author().setName("Nadie").setSurname("Nobody")))
                        .setIsbn("12-9999-92")
                        .setReleaseDate(LocalDate.now())
                        .setPublisher(new Publisher().setName("Editorial 7").setCountry("ES").setOnline(true)),
                new Book()
                        .setId(BOOK_ID_4)
                        .setTitle("Libro prueba mongo 4")
                        .setAuthors(Arrays.asList(new Author().setName("Perry").setSurname("Mason")))
                        .setIsbn("22-34565-12")
                        .setReleaseDate(LocalDate.now())
                        .setPublisher(new Publisher().setName("Editorial 33").setCountry("US").setOnline(true)))
                .forEach(bookRepository::save);
    }

}
