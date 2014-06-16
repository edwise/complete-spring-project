package com.edwise.completespring.entities;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by Edu on 07/06/2014.
 */
public class BookBuilder {

    private Book book;

    public BookBuilder() {
        book = new Book();
    }

    public BookBuilder id(Long id) {
        book.setId(id);
        return this;
    }

    public BookBuilder title(String title) {
        book.setTitle(title);
        return this;
    }

    public BookBuilder authors(List<Author> authors) {
        book.setAuthors(authors);
        return this;
    }

    public BookBuilder isbn(String isbn) {
        book.setIsbn(isbn);
        return this;
    }

    public BookBuilder releaseDate(LocalDate releaseDate) {
        book.setReleaseDate(releaseDate);
        return this;
    }

    public BookBuilder publisher(Publisher publisher) {
        book.setPublisher(publisher);
        return this;
    }

    public Book build() {
        return book;
    }
}
