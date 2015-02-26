package com.edwise.completespring.services;

import com.edwise.completespring.entities.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService extends Service<Book, Long> {

    List<Book> findByTitle(String title);

    List<Book> findByReleaseDate(LocalDate releaseDate);

    Book create(Book book);
}
