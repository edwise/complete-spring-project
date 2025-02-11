package com.edwise.completespring.controllers;

import com.edwise.completespring.assemblers.FooResource;
import com.edwise.completespring.assemblers.FooResourceAssembler;
import com.edwise.completespring.entities.Foo;
import com.edwise.completespring.exceptions.InvalidRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/foos/")
@Tag(name = "foos", description = "Foo API")
@Slf4j
public class FooController {
    private static final int RESPONSE_CODE_OK = 200;
    private static final int RESPONSE_CODE_CREATED = 201;
    private static final int RESPONSE_CODE_NO_RESPONSE = 204;
    private static final String TEST_ATTRIBUTE_1 = "AttText1";

    @Autowired
    private FooResourceAssembler fooResourceAssembler;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Foos", description = "Returns all foos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exits one foo at least", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FooResource.class)))
    })
    public ResponseEntity<List<FooResource>> getAll() {
        List<Foo> foos = List.of(
                new Foo().setId(1L).setSampleTextAttribute(TEST_ATTRIBUTE_1).setSampleLocalDateAttribute(LocalDate.now()),
                new Foo().setId(2L).setSampleTextAttribute(TEST_ATTRIBUTE_1).setSampleLocalDateAttribute(LocalDate.now())
        );
        List<FooResource> resourceList = fooResourceAssembler.toModels(foos);

        log.info("Foos found: {}", foos);
        return new ResponseEntity<>(resourceList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get one Foo", description = "Returns one foo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exists this foo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FooResource.class)))
    })
    public ResponseEntity<FooResource> getFoo(@Parameter(description = "The id of the foo to return")
                                              @PathVariable long id) {
        FooResource resource = fooResourceAssembler.toModel(
                new Foo().setId(id).setSampleTextAttribute(TEST_ATTRIBUTE_1).setSampleLocalDateAttribute(LocalDate.now())
        );

        log.info("Foo found: {}", resource.getFoo());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Foo", description = "Create a foo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successful create of a foo")
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
    @Operation(summary = "Update Foo", description = "Update a foo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successful update of foo")
    })
    public void updateFoo(@Parameter(description = "The id of the foo to update")
                          @PathVariable long id,
                          @Valid @RequestBody Foo foo, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException(errors);
        }
        log.info("Foo updated: {}", foo.setId(id).toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Foo", description = "Delete a foo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successful delete of a foo")
    })
    public void deleteFoo(@Parameter(description = "The id of the foo to delete")
                          @PathVariable long id) {

        log.info("Foo deleted: {}", id);
    }

    private HttpHeaders createHttpHeadersWithLocation(Foo foo) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Optional<Link> selfBookLink = fooResourceAssembler.toModel(foo).getLink("self");
        httpHeaders.setLocation(URI.create(selfBookLink.map(Link::getHref).orElse("")));
        return httpHeaders;
    }
}
