package com.edwise.completespring.assemblers;

import com.edwise.completespring.controllers.FooController;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.Foo;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FooResourceAssembler extends RepresentationModelAssemblerSupport<Foo, FooResource> {

    public FooResourceAssembler() {
        super(FooController.class, FooResource.class);
    }

    @Override
    protected FooResource instantiateModel(Foo foo) {
        FooResource fooResource = super.instantiateModel(foo);
        fooResource.setFoo(foo);

        return fooResource;
    }

    public FooResource toModel(Foo foo) {
        return this.createModelWithId(foo.getId(), foo);
    }

    public List<FooResource> toModels(List<Foo> foos) {
        return foos.stream().map(this::toModel).toList();
    }
}
