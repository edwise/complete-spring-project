package com.edwise.completespring.assemblers;

import com.edwise.completespring.entities.Book;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BookResource extends ResourceSupport {
    private Book book;
}
