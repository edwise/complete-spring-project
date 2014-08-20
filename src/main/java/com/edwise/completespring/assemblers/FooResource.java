package com.edwise.completespring.assemblers;

import com.edwise.completespring.entities.Foo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by user EAnton on 25/04/2014.
 */
@Getter
@Setter
@Accessors(chain = true)
public class FooResource extends ResourceSupport {
    private Foo foo;
}
