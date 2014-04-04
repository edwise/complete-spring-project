package com.edwise.completespring.controllers;

import com.edwise.completespring.entities.Book;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user EAnton on 04/04/2014.
 */
@RestController
@RequestMapping("/api/book")
public class BookController {
    private final Logger log = LoggerFactory.getLogger(BookController.class);

    @RequestMapping(method = RequestMethod.GET)
    public List<Book> getAllBooks() {
        // TODO implementar en condiciones...
        return Arrays.asList(new Book(3, "Libro prueba", Arrays.asList("Edu"), "11-333-12", new LocalDate()),
                new Book(400, "Libro prueba 2", Arrays.asList("Otro", "S. King"), "12-1234-12", new LocalDate()),
                new Book(14, "Libro prueba 3", Arrays.asList("Nadie"), "12-9999-92", new LocalDate()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Book getBook(@PathVariable long id) {
        // TODO implementar en condiciones...
        return new Book(id, "Libro prueba", Arrays.asList("Edu"), "12-2344-12", new LocalDate());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void createBook(@RequestBody Book book) {
        // TODO implementar en condiciones...
        log.info("Book created: " + book.toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public void updateBook(@PathVariable long id, @RequestBody Book book) {
        // TODO implementar en condiciones...
        book.setId(id);
        log.info("Book updated: " + book.toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteBook(@PathVariable long id) {
        // TODO implementar en condiciones...
        log.info("Book deleted: " + id);
    }
}
