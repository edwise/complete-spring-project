package com.edwise.completespring.controllers;

import com.edwise.completespring.assemblers.BookResource;
import com.edwise.completespring.assemblers.BookResourceAssembler;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.exceptions.InvalidRequestException;
import com.edwise.completespring.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books/")
@Slf4j
@Tag(name = "Books API", description = "Books management API")
public class BookController {

    @Autowired
    private BookResourceAssembler bookResourceAssembler;

    @Autowired
    private BookService bookService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Books", description = "Returns all books")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exits one book at least", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResource.class)))
    })
    public ResponseEntity<List<BookResource>> getAll() {
        List<Book> books = bookService.findAll();
        List<BookResource> resourceList = bookResourceAssembler.toModels(books);

        log.info("Books found: {}", books);
        return new ResponseEntity<>(resourceList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get one Book", description = "Returns one book", responses = {
            @ApiResponse(responseCode = "200", description = "Exists this book", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResource.class)))
    })
    public ResponseEntity<BookResource> getBook(@Parameter(description = "The id of the book to return") @PathVariable long id) {
        Book book = bookService.findOne(id);

        log.info("Book found: {}", book);
        return new ResponseEntity<>(bookResourceAssembler.toModel(book), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Book", description = "Create a book")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successful create of a book")
    })
    public ResponseEntity<BookResource> createBook(@Valid @RequestBody Book book, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException(errors);
        }
        Book bookCreated = bookService.create(book);

        log.info("Book created: {}", bookCreated.toString());
        return new ResponseEntity<>(createHttpHeadersWithLocation(bookCreated), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Book", description = "Update a book")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successful update of book")
    })
    public void updateBook(@Parameter(description = "The id of the book to update") @PathVariable long id,
                           @Valid @RequestBody Book book, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException(errors);
        }
        Book dbBook = bookService.findOne(id);
        dbBook = bookService.save(dbBook.copyFrom(book));

        log.info("Book updated: {}", dbBook.toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Book", description = "Delete a book")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successful delete of a book")
    })
    public void deleteBook(@Parameter(description = "The id of the book to delete") @PathVariable long id) {
        bookService.delete(id);

        log.info("Book deleted: {}", id);
    }

    private HttpHeaders createHttpHeadersWithLocation(Book book) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Optional<Link> selfBookLink = bookResourceAssembler.toModel(book).getLink("self");
        httpHeaders.setLocation(URI.create(selfBookLink.map(Link::getHref).orElse("")));
        return httpHeaders;
    }
}
