package com.edwise.completespring.controllers;

import com.edwise.completespring.assemblers.FooResource;
import com.edwise.completespring.assemblers.FooResourceAssembler;
import com.edwise.completespring.entities.Foo;
import com.edwise.completespring.exceptions.InvalidRequestException;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/foos/")
@Api(value = "foos", description = "Foo API", produces = "application/json")
@Slf4j
public class FooController {
    private static final int RESPONSE_CODE_OK = 200;
    private static final int RESPONSE_CODE_CREATED = 201;
    private static final int RESPONSE_CODE_NO_RESPONSE = 204;
    private static final String TEST_ATTRIBUTE_1 = "AttText1";

    @Autowired
    private FooResourceAssembler fooResourceAssembler;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Foos", notes = "Returns all foos")
    @ApiResponses({
            @ApiResponse(code = RESPONSE_CODE_OK, response = FooResource.class, message = "Exits one foo at least")
    })
    public ResponseEntity<List<FooResource>> getAll() {
        List<Foo> foos = Arrays.asList(
                new Foo().setId(1L).setSampleTextAttribute(TEST_ATTRIBUTE_1).setSampleLocalDateAttribute(LocalDate.now()),
                new Foo().setId(2L).setSampleTextAttribute(TEST_ATTRIBUTE_1).setSampleLocalDateAttribute(LocalDate.now())
        );
        List<FooResource> resourceList = fooResourceAssembler.toResources(foos);

        log.info("Foos found: {}", foos);
        return new ResponseEntity<>(resourceList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get one Foo", response = FooResource.class, notes = "Returns one foo")
    @ApiResponses({
            @ApiResponse(code = RESPONSE_CODE_OK, message = "Exists this foo")
    })
    public ResponseEntity<FooResource> getFoo(@ApiParam(defaultValue = "1", value = "The id of the foo to return")
                                              @PathVariable long id) {
        FooResource resource = fooResourceAssembler.toResource(
                new Foo().setId(id).setSampleTextAttribute(TEST_ATTRIBUTE_1).setSampleLocalDateAttribute(LocalDate.now())
        );

        log.info("Foo found: {}", resource.getFoo());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create Foo", notes = "Create a foo")
    @ApiResponses({
            @ApiResponse(code = RESPONSE_CODE_CREATED, message = "Successful create of a foo")
    })
    public ResponseEntity<FooResource> createFoo(@Valid @RequestBody Foo foo, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException(errors);
        }
        log.info("Foo created: {}", foo.toString());
        return new ResponseEntity<>(createHttpHeadersWithLocation(foo.setId(1L)), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Foo", notes = "Update a foo")
    @ApiResponses({
            @ApiResponse(code = RESPONSE_CODE_NO_RESPONSE, message = "Successful update of foo")
    })
    public void updateFoo(@ApiParam(defaultValue = "1", value = "The id of the foo to update")
                          @PathVariable long id,
                          @Valid @RequestBody Foo foo, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException(errors);
        }
        log.info("Foo updated: {}", foo.setId(id).toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Foo", notes = "Delete a foo")
    @ApiResponses({
            @ApiResponse(code = RESPONSE_CODE_NO_RESPONSE, message = "Successful delete of a foo")
    })
    public void deleteFoo(@ApiParam(defaultValue = "1", value = "The id of the foo to delete")
                          @PathVariable long id) {

        log.info("Foo deleted: {}", id);
    }

    private HttpHeaders createHttpHeadersWithLocation(Foo foo) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Link selfBookLink = fooResourceAssembler.toResource(foo).getLink("self");
        httpHeaders.setLocation(URI.create(selfBookLink != null ? selfBookLink.getHref() : ""));
        return httpHeaders;
    }
}
