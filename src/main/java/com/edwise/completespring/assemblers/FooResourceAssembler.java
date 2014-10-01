package com.edwise.completespring.assemblers;

import com.edwise.completespring.controllers.FooController;
import com.edwise.completespring.entities.Foo;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FooResourceAssembler extends ResourceAssemblerSupport<Foo, FooResource> {

    public FooResourceAssembler() {
        super(FooController.class, FooResource.class);
    }

    @Override
    protected FooResource instantiateResource(Foo foo) {
        FooResource fooResource = super.instantiateResource(foo);
        fooResource.setFoo(foo);

        return fooResource;
    }

    public FooResource toResource(Foo foo) {
        return this.createResourceWithId(foo.getId(), foo);
    }
}
