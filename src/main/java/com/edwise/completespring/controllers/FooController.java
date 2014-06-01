package com.edwise.completespring.controllers;

import com.edwise.completespring.assemblers.FooResource;
import com.edwise.completespring.assemblers.FooResourceAssembler;
import com.edwise.completespring.entities.Foo;
import com.edwise.completespring.exceptions.InvalidRequestException;
import com.wordnik.swagger.annotations.*;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user EAnton on 25/04/2014.
 *
 * Url: /api/foo
 */
@RestController
@Api(value = "foos", description = "Foo API")
public class FooController {
    private final Logger log = LoggerFactory.getLogger(FooController.class);

    @Autowired
    private FooResourceAssembler fooResourceAssembler;

    @RequestMapping(method = RequestMethod.GET, value = "/api/foo")
    @ApiOperation(value = "Get Foos", notes = "Returns all foos")
    @ApiResponses({
            @ApiResponse(code = 200, response = FooResource.class, message = "Exits one foo at least")
    })
    public ResponseEntity<List<FooResource>> getAll() {
        List<Foo> foos = Arrays.asList(
                new Foo().setId(1l).setSampleTextAttribute("AttText1").setSampleLocalDateAttribute(new LocalDate()),
                new Foo().setId(2l).setSampleTextAttribute("AttText1").setSampleLocalDateAttribute(new LocalDate())
        );

        List<FooResource> resourceList = fooResourceAssembler.toResources(foos);
        return new ResponseEntity<>(resourceList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/foo/{id}")
    @ApiOperation(value = "Get one Foo", response = FooResource.class, notes = "Returns one foo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exists this foo")
    })
    public ResponseEntity<FooResource> getFoo(@ApiParam(defaultValue = "1", value = "The id of the foo to return")
                                              @PathVariable long id) {
        FooResource resource = fooResourceAssembler.toResource(
                new Foo().setId(1l).setSampleTextAttribute("AttText1").setSampleLocalDateAttribute(new LocalDate())
        );
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/api/foo")
    @ApiOperation(value = "Create Foo", notes = "Create a foo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successful create of a foo")
    })
    public void createFoo(@Valid @RequestBody Foo foo, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException(errors);
        }
        log.info("Foo created: " + foo.toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, value = "/api/foo/{id}")
    @ApiOperation(value = "Update Foo", notes = "Update a foo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successful update of foo")
    })
    public void updateFoo(@ApiParam(defaultValue = "1", value = "The id of the foo to update")
                          @PathVariable long id,
                          @Valid @RequestBody Foo foo, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException(errors);
        }
        log.info("Foo updated: " + foo.toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/api/foo/{id}")
    @ApiOperation(value = "Delete Foo", notes = "Delete a foo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successful delete of a foo")
    })
    public void deleteFoo(@ApiParam(defaultValue = "1", value = "The id of the foo to delete")
                          @PathVariable long id) {

        log.info("Foo deleted: " + id);
    }
}
