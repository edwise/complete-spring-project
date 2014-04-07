package com.edwise.completespring.repositories;

import com.edwise.completespring.entities.Book;
import org.joda.time.LocalDate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by user EAnton on 07/04/2014.
 */
public interface BookRepository extends MongoRepository<Book, Long> {
    public static final String BOOK_COLLECTION = "books";

    public List<Book> findByTitle(String title);
    public List<Book> findByReleaseDate(LocalDate releaseDate);

}
