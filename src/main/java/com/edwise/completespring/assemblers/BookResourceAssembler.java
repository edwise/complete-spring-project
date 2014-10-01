package com.edwise.completespring.assemblers;

import com.edwise.completespring.controllers.BookController;
import com.edwise.completespring.entities.Book;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class BookResourceAssembler extends ResourceAssemblerSupport<Book, BookResource> {

    public BookResourceAssembler() {
        super(BookController.class, BookResource.class);
    }

    @Override
    protected BookResource instantiateResource(Book book) {
        BookResource bookResource = super.instantiateResource(book);
        bookResource.setBook(book);

        return bookResource;
    }

    public BookResource toResource(Book book) {
        return createResourceWithId(book.getId(), book);
    }
}
