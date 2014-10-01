package com.edwise.completespring.controllers;

import com.edwise.completespring.assemblers.FooResource;
import com.edwise.completespring.assemblers.FooResourceAssembler;
import com.edwise.completespring.entities.Foo;
import com.edwise.completespring.entities.FooTest;
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

public class FooControllerTest {
    private static final long FOO_ID_TEST1 = 1l;
    private static final String FOO_TEXT_ATTR_TEST1 = "AttText1";
    private static final LocalDate DATE_TEST1 = new LocalDate(2013, 1, 26);
    public static final String RIGHT_URL_FOO_1 = "http://localhost/api/foo/1";

    private MockHttpServletRequest request;

    private FooController controller;

    @Mock
    BindingResult errors;

    @Before
    public void setUp() {
        this.request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));
        MockitoAnnotations.initMocks(this);
        this.controller = new FooController();
        // TODO revisar si sacar a atributo ese resource assembler
        ReflectionTestUtils.setField(controller, "fooResourceAssembler", new FooResourceAssembler());
    }

    @After
    public void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test(expected = InvalidRequestException.class)
    public void testUpdateInvalidRequest() {
        Foo fooReq = FooTest.createFoo(FOO_ID_TEST1, null, null);
        when(errors.hasErrors()).thenReturn(true);

        controller.updateFoo(FOO_ID_TEST1, fooReq, errors);
    }


    @Test
    public void testCreate() {
        Foo FooReq = FooTest.createFoo(FOO_ID_TEST1, FOO_TEXT_ATTR_TEST1, DATE_TEST1);
        when(errors.hasErrors()).thenReturn(false);

        controller.createFoo(FooReq, errors);

        verify(errors, times(1)).hasErrors();
        assertFalse(errors.hasErrors());
    }

    @Test(expected = InvalidRequestException.class)
    public void testCreateInvalidRequest() {
        Foo FooReq = FooTest.createFoo(null, null, null);
        when(errors.hasErrors()).thenReturn(true);

        controller.createFoo(FooReq, errors);

        verify(errors, times(1)).hasErrors();
    }

    @Test
    public void testUpdate() {
        Foo FooReq = FooTest.createFoo(FOO_ID_TEST1, FOO_TEXT_ATTR_TEST1, DATE_TEST1);

        controller.updateFoo(FOO_ID_TEST1, FooReq, errors);

        assertTrue(true);
    }


    @Test
    public void testGet() {
        ResponseEntity<FooResource> result = controller.getFoo(FOO_ID_TEST1);

        assertEquals(RIGHT_URL_FOO_1, result.getBody().getLink("self").getHref());
    }

    @Test
    public void testDelete() {
        controller.deleteFoo(FOO_ID_TEST1);

        assertTrue(true);
    }

    @Test
    public void testFindAll() {
        ResponseEntity<List<FooResource>> result = controller.getAll();

        assertNotNull(result.getBody());
    }
}
