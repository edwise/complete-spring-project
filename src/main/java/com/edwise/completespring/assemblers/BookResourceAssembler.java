package com.edwise.completespring.assemblers;

import com.edwise.completespring.controllers.BookController;
import com.edwise.completespring.entities.Book;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.List;

@Component
public class BookResourceAssembler extends RepresentationModelAssemblerSupport<Book, BookResource> {

    public BookResourceAssembler() {
        super(BookController.class, BookResource.class);
    }

    @Override
    protected BookResource instantiateModel(Book book) {
        BookResource bookResource = super.instantiateModel(book);
        bookResource.setBook(book);

        return bookResource;
    }

    @Override
    public BookResource toModel(Book book) {
        return createModelWithId(book.getId(), book);
    }

    public List<BookResource> toModels(List<Book> books) {
        return books.stream().map(this::toModel).toList();
    }
}
