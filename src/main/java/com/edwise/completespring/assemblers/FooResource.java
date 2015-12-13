package com.edwise.completespring.assemblers;

import com.edwise.completespring.entities.Foo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Setter
@Accessors(chain = true)
public class FooResource extends ResourceSupport {
    private Foo foo;
}
