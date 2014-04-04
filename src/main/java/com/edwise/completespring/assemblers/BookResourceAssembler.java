package com.edwise.completespring.assemblers;

import com.edwise.completespring.controllers.BookController;
import com.edwise.completespring.entities.Book;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by user EAnton on 04/04/2014.
 */
@Component
public class BookResourceAssembler extends ResourceAssemblerSupport<Book, BookResource> {

    public BookResourceAssembler() {
        super(BookController.class, BookResource.class);
    }

    public BookResource toResource(Book book) {
        BookResource result = instantiateResource(book);
        result.book = book;
        result.add(linkTo(BookController.class).slash(book.getId()).withSelfRel());

        return result;
    }
}
