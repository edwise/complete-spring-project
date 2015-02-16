package com.edwise.completespring.repositories;

import com.edwise.completespring.entities.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends MongoRepository<Book, Long> {

    List<Book> findByTitle(String title);
    List<Book> findByReleaseDate(LocalDate releaseDate);

}
