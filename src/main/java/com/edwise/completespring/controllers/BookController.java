package com.edwise.completespring.controllers;

import com.edwise.completespring.assemblers.BookResource;
import com.edwise.completespring.assemblers.BookResourceAssembler;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.exceptions.InvalidRequestException;
import com.edwise.completespring.services.BookService;
import com.wordnik.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by user EAnton on 04/04/2014.
 *
 * Url: /api/book
 */
@RestController
@Api(value = "books", description = "Books API")
public class BookController {
    private final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookResourceAssembler bookResourceAssembler;

    @Autowired
    private BookService bookService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/book", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Books", notes = "Returns all books")
    @ApiResponses({
            @ApiResponse(code = 200, response = BookResource.class, message = "Exits one book at least")
    })
    public ResponseEntity<List<BookResource>> getAll() {
        List<Book> books = bookService.findAll();

        List<BookResource> resourceList = bookResourceAssembler.toResources(books);
        return new ResponseEntity<>(resourceList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get one Book", response = BookResource.class, notes = "Returns one book")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exists this book")
    })
    public ResponseEntity<BookResource> getBook(@ApiParam(defaultValue = "1", value = "The id of the book to return")
                                                @PathVariable long id) {
        Book book = bookService.findOne(id);

        BookResource resource = bookResourceAssembler.toResource(book);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/api/book", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create Book", notes = "Create a book")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successful create of a book")
    })
    public void createBook(@Valid @RequestBody Book book, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException(errors);
        }
        Book bookCreated = bookService.create(book);

        log.info("Book created: " + bookCreated.toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, value = "/api/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Book", notes = "Update a book")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successful update of book")
    })
    public void updateBook(@ApiParam(defaultValue = "1", value = "The id of the book to update")
                           @PathVariable long id,
                           @Valid @RequestBody Book book, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException(errors);
        }
        Book dbBook = bookService.findOne(id);
        dbBook = bookService.save(dbBook.copyFrom(book));

        log.info("Book updated: " + dbBook.toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/api/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Book", notes = "Delete a book")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successful delete of a book")
    })
    public void deleteBook(@ApiParam(defaultValue = "1", value = "The id of the book to delete")
                           @PathVariable long id) {
        bookService.delete(id);

        log.info("Book deleted: " + id);
    }
}
