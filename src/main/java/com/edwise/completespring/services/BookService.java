package com.edwise.completespring.services;

import com.edwise.completespring.entities.Book;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by user EAnton on 07/04/2014.
 */
public interface BookService extends Service<Book, Long> {

    public Book findByTitle(String title);

    public List<Book> findByReleaseDate(LocalDate releaseDate);

    public Book create(Book book);
}
