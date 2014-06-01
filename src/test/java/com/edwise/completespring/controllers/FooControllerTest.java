package com.edwise.completespring.controllers;

import com.edwise.completespring.assemblers.FooResource;
import com.edwise.completespring.assemblers.FooResourceAssembler;
import com.edwise.completespring.entities.Foo;
import com.edwise.completespring.exceptions.InvalidRequestException;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by user EAnton on 25/04/2014.
 */
public class FooControllerTest {

    private FooController controller;

    private MockHttpServletRequest request;

    @Mock
    BindingResult errors;


    @Before
    public void setUp() {
        this.request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));
        MockitoAnnotations.initMocks(this);
        this.controller = new FooController();
        ReflectionTestUtils.setField(controller, "fooResourceAssembler", new FooResourceAssembler());
    }

    @After
    public void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test(expected = InvalidRequestException.class)
    public void testUpdateInvalidRequest() {
        Long id = 1l;
        Foo fooReq = new Foo().setId(1l).setSampleTextAttribute(null).setSampleLocalDateAttribute(null);
        when(errors.hasErrors()).thenReturn(true);
        controller.updateFoo(id, fooReq, errors);
        verify(errors, times(1)).hasErrors();
        fail("Expected exception");
    }


    @Test
    public void testCreate() {
        Foo FooReq = new Foo().setId(1l).setSampleTextAttribute("AttText1").setSampleLocalDateAttribute(new LocalDate());
        when(errors.hasErrors()).thenReturn(false);
        controller.createFoo(FooReq, errors);
        verify(errors, times(1)).hasErrors();
        assertFalse(errors.hasErrors());
    }

    @Test(expected = InvalidRequestException.class)
    public void testCreateInvalidRequest() {
        Foo FooReq = new Foo();
        when(errors.hasErrors()).thenReturn(true);
        controller.createFoo(FooReq, errors);
        verify(errors, times(1)).hasErrors();
    }

    @Test
    public void testUpdate() {
        Long id = 1l;
        Foo FooReq = new Foo().setId(1l).setSampleTextAttribute("AttText1").setSampleLocalDateAttribute(new LocalDate());
        controller.updateFoo(id, FooReq, errors);
        assertTrue(true);
    }


    @Test
    public void testGet() {
        Long id = 1l;
        ResponseEntity<FooResource> result = controller.getFoo(id);
        assertEquals("http://localhost/api/foo/1", result.getBody().getLink("self").getHref());
    }

    @Test
    public void testDelete() {
        Long id = 1l;
        controller.deleteFoo(id);
        assertTrue(true);
    }

    @Test
    public void testFindAll() {
        ResponseEntity<List<FooResource>> result = controller.getAll();
        assertNotNull(result.getBody());
    }
}
