package com.edwise.completespring.repositories;

import com.edwise.completespring.entities.Book;
import org.joda.time.LocalDate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by user EAnton on 07/04/2014.
 */
public interface BookRepository extends MongoRepository<Book, Long> {
    String BOOK_COLLECTION = "books";

    List<Book> findByTitle(String title);
    List<Book> findByReleaseDate(LocalDate releaseDate);

}
