package com.edwise.completespring.testutil;

import com.edwise.completespring.entities.Author;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.Publisher;

import java.time.LocalDate;
import java.util.List;

public class BookBuilder {

    private final Book book;

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
