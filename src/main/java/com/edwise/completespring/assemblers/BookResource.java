package com.edwise.completespring.assemblers;

import com.edwise.completespring.entities.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Accessors(chain = true)
public class BookResource extends RepresentationModel<BookResource> {
    private Book book;
}
