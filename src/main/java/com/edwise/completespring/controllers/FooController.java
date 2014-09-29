package com.edwise.completespring.controllers;

import com.edwise.completespring.assemblers.FooResource;
import com.edwise.completespring.assemblers.FooResourceAssembler;
import com.edwise.completespring.entities.Foo;
import com.edwise.completespring.exceptions.InvalidRequestException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user EAnton on 25/04/2014.
 *
 * Url: /api/foo
 */
@RestController
@RequestMapping(value = "/api/foo/")
@Api(value = "foos", description = "Foo API")
public class FooController {
    private static final Logger LOG = LoggerFactory.getLogger(FooController.class);

    private static final int RESPONSE_CODE_OK = 200;
    private static final int RESPONSE_CODE_NO_RESPONSE = 204;
    private static final String TEST_ATTRIBUTE_1 = "AttText1";

    @Autowired
    private FooResourceAssembler fooResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get Foos", notes = "Returns all foos")
    @ApiResponses({
            @ApiResponse(code = RESPONSE_CODE_OK, response = FooResource.class, message = "Exits one foo at least")
    })
    public ResponseEntity<List<FooResource>> getAll() {
        List<Foo> foos = Arrays.asList(
                new Foo().setId(1L).setSampleTextAttribute(TEST_ATTRIBUTE_1).setSampleLocalDateAttribute(new LocalDate()),
                new Foo().setId(2L).setSampleTextAttribute(TEST_ATTRIBUTE_1).setSampleLocalDateAttribute(new LocalDate())
        );

        List<FooResource> resourceList = fooResourceAssembler.toResources(foos);
        return new ResponseEntity<>(resourceList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    @ApiOperation(value = "Get one Foo", response = FooResource.class, notes = "Returns one foo")
    @ApiResponses({
            @ApiResponse(code = RESPONSE_CODE_OK, message = "Exists this foo")
    })
    public ResponseEntity<FooResource> getFoo(@ApiParam(defaultValue = "1", value = "The id of the foo to return")
                                              @PathVariable long id) {
        FooResource resource = fooResourceAssembler.toResource(
                new Foo().setId(1L).setSampleTextAttribute(TEST_ATTRIBUTE_1).setSampleLocalDateAttribute(new LocalDate())
        );
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create Foo", notes = "Create a foo")
    @ApiResponses({
            @ApiResponse(code = RESPONSE_CODE_NO_RESPONSE, message = "Successful create of a foo")
    })
    public void createFoo(@Valid @RequestBody Foo foo, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException(errors);
        }
        LOG.info("Foo created: " + foo.toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
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
        LOG.info("Foo updated: " + foo.toString());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    @ApiOperation(value = "Delete Foo", notes = "Delete a foo")
    @ApiResponses({
            @ApiResponse(code = RESPONSE_CODE_NO_RESPONSE, message = "Successful delete of a foo")
    })
    public void deleteFoo(@ApiParam(defaultValue = "1", value = "The id of the foo to delete")
                          @PathVariable long id) {

        LOG.info("Foo deleted: " + id);
    }
}
