package com.edwise.completespring.assemblers;

import com.edwise.completespring.entities.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by user EAnton on 04/04/2014.
 */
@Getter
@Setter
@Accessors(chain = true)
public class BookResource extends ResourceSupport {
    private Book book;
}
