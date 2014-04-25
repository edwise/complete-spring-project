package com.edwise.completespring.assemblers;

import com.edwise.completespring.controllers.FooController;
import com.edwise.completespring.entities.Foo;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by user EAnton on 25/04/2014.
 */
@Component
public class FooResourceAssembler extends ResourceAssemblerSupport<Foo, FooResource> {

    public FooResourceAssembler() {
        super(FooController.class, FooResource.class);
    }

    public FooResource toResource(Foo foo) {
        FooResource result = instantiateResource(foo);
        result.foo = foo;
        result.add(linkTo(FooController.class).slash(foo.getId()).withSelfRel());

        return result;
    }
}
