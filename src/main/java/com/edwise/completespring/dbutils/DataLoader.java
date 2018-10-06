package com.edwise.completespring.dbutils;

import com.edwise.completespring.entities.*;
import com.edwise.completespring.repositories.BookRepository;
import com.edwise.completespring.repositories.SequenceIdRepository;
import com.edwise.completespring.repositories.UserAccountRepository;
import com.edwise.completespring.services.impl.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Component
public class DataLoader {
    private static final String USERACCOUNTS_COLLECTION = "users";
    private static final Long BOOKS_INITIAL_SEQUENCE = 4L;
    private static final Long USERACCOUNTS_INITIAL_SEQUENCE = 3L;
    public static final Long BOOK_ID_1 = 1L;
    public static final Long BOOK_ID_2 = 2L;
    public static final Long BOOK_ID_3 = 3L;
    private static final Long BOOK_ID_4 = 4L;
    private static final Long USER_ID_1 = 1L;
    private static final Long USER_ID_2 = 2L;
    public static final String USER = "user1";
    public static final String PASSWORD_USER = "password1";
    public static final String ADMIN = "admin";
    public static final String PASSWORD_ADMIN = "password1234";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private SequenceIdRepository sequenceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void fillDBData() {
        log.info("Filling DB data...");
        fillDBUsersData();
        fillDBBooksData();
        log.info("DB filled with data.");
    }

    private void fillDBUsersData() {
        sequenceRepository.save(new SequenceId()
                .setId(USERACCOUNTS_COLLECTION)
                .setSeq(USERACCOUNTS_INITIAL_SEQUENCE)
        );
        userAccountRepository.deleteAll();
        Arrays.asList(
                new UserAccount()
                        .setId(USER_ID_1)
                        .setUsername(USER)
                        .setPassword(passwordEncoder.encode(PASSWORD_USER))
                        .setUserType(UserAccountType.REST_USER),
                new UserAccount()
                        .setId(USER_ID_2)
                        .setUsername(ADMIN)
                        .setPassword(passwordEncoder.encode(PASSWORD_ADMIN))
                        .setUserType(UserAccountType.ADMIN_USER))
                .forEach(userAccountRepository::save);
    }

    private void fillDBBooksData() {
        sequenceRepository.save(new SequenceId()
                .setId(BookServiceImpl.BOOK_COLLECTION)
                .setSeq(BOOKS_INITIAL_SEQUENCE)
        );
        bookRepository.deleteAll();
        Arrays.asList(
                new Book()
                        .setId(BOOK_ID_1)
                        .setTitle("Libro prueba mongo")
                        .setAuthors(Collections.singletonList(new Author().setName("Edu").setSurname("Antón")))
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
                        .setAuthors(Collections.singletonList(new Author().setName("Nadie").setSurname("Nobody")))
                        .setIsbn("12-9999-92")
                        .setReleaseDate(LocalDate.now())
                        .setPublisher(new Publisher().setName("Editorial 7").setCountry("ES").setOnline(true)),
                new Book()
                        .setId(BOOK_ID_4)
                        .setTitle("Libro prueba mongo 4")
                        .setAuthors(Collections.singletonList(new Author().setName("Perry").setSurname("Mason")))
                        .setIsbn("22-34565-12")
                        .setReleaseDate(LocalDate.now())
                        .setPublisher(new Publisher().setName("Editorial 33").setCountry("US").setOnline(true)))
                .forEach(bookRepository::save);
    }
}
