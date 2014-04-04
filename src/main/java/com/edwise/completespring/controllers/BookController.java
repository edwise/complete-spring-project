package com.edwise.completespring.controllers;

import com.edwise.completespring.assemblers.BookResource;
import com.edwise.completespring.assemblers.BookResourceAssembler;
import com.edwise.completespring.entities.Book;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user EAnton on 04/04/2014.
 */
@RestController
@RequestMapping("/api/book")
@Api(value = "books", description = "Books API")
public class BookController {
    private final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookResourceAssembler bookResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get Books", notes = "Returns all books")
    public ResponseEntity<List<BookResource>> getAllBooks() {
        // TODO implementar en condiciones...
        List books = Arrays.asList(new Book(3, "Libro prueba", Arrays.asList("Edu"), "11-333-12", new LocalDate()),
                new Book(400, "Libro prueba 2", Arrays.asList("Otro", "S. King"), "12-1234-12", new LocalDate()),
                new Book(14, "Libro prueba 3", Arrays.asList("Nadie"), "12-9999-92", new LocalDate()));

        List<BookResource> resourceList = bookResourceAssembler.toResources(books);
        return new ResponseEntity<List<BookResource>>(resourceList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiOperation(value = "Get one Book", notes = "Returns one book")
    public ResponseEntity<BookResource> getBook(@PathVariable long id) {
        // TODO implementar en condiciones...
        BookResource resource = bookResourceAssembler.toResource(
                new Book(id, "Libro prueba", Arrays.asList("Edu"), "12-2344-12", new LocalDate()));
        return new ResponseEntity<BookResource>(resource, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create Book", notes = "Create a book")
    public void createBook(@RequestBody Book book) {
        // TODO implementar en condiciones...
        log.info("Book created: " + book.toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @ApiOperation(value = "Update Book", notes = "Update a book")
    public void updateBook(@PathVariable long id, @RequestBody Book book) {
        // TODO implementar en condiciones...
        book.setId(id);
        log.info("Book updated: " + book.toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ApiOperation(value = "Delete Book", notes = "Delete a book")
    public void deleteBook(@PathVariable long id) {
        // TODO implementar en condiciones...
        log.info("Book deleted: " + id);
    }
}
