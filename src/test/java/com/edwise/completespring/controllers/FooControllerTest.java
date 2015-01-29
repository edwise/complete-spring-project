package com.edwise.completespring.controllers;

import com.edwise.completespring.assemblers.FooResource;
import com.edwise.completespring.assemblers.FooResourceAssembler;
import com.edwise.completespring.entities.Foo;
import com.edwise.completespring.entities.FooTest;
import com.edwise.completespring.exceptions.InvalidRequestException;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FooControllerTest {
    private static final long FOO_ID_TEST1 = 1l;
    private static final String FOO_TEXT_ATTR_TEST1 = "AttText1";
    private static final LocalDate DATE_TEST1 = new LocalDate(2013, 1, 26);

    @Mock
    BindingResult errors;

    @Mock
    FooResourceAssembler fooResourceAssembler;

    @InjectMocks
    private FooController controller = new FooController();

    @Before
    public void setUp() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test(expected = InvalidRequestException.class)
    public void testUpdateInvalidRequest() {
        Foo fooReq = FooTest.createFoo(FOO_ID_TEST1, null, null);
        when(errors.hasErrors()).thenReturn(true);

        controller.updateFoo(FOO_ID_TEST1, fooReq, errors);
    }


    @Test
    public void testCreate() {
        Foo fooReq = FooTest.createFoo(null, FOO_TEXT_ATTR_TEST1, DATE_TEST1);
        Foo fooCreated = FooTest.createFoo(FOO_ID_TEST1, FOO_TEXT_ATTR_TEST1, DATE_TEST1);
        when(errors.hasErrors()).thenReturn(false);
        when(fooResourceAssembler.toResource(any(Foo.class))).thenReturn(createFooResourceWithLink(fooCreated));

        ResponseEntity<FooResource> result = controller.createFoo(fooReq, errors);

        assertNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertThat(result.getHeaders().getLocation().toString(), containsString("/api/foos/" + FOO_ID_TEST1));
        verify(fooResourceAssembler, times(1)).toResource(fooCreated);
        verify(errors, times(1)).hasErrors();
    }

    @Test(expected = InvalidRequestException.class)
    public void testCreateInvalidRequest() {
        Foo FooReq = FooTest.createFoo(null, null, null);
        when(errors.hasErrors()).thenReturn(true);

        controller.createFoo(FooReq, errors);
    }

    @Test
    public void testUpdate() {
        Foo FooReq = FooTest.createFoo(FOO_ID_TEST1, FOO_TEXT_ATTR_TEST1, DATE_TEST1);

        controller.updateFoo(FOO_ID_TEST1, FooReq, errors);
    }

    @Test
    public void testGet() {
        when(fooResourceAssembler.toResource(any(Foo.class)))
                .thenReturn(new FooResource().setFoo(FooTest.createFoo(FOO_ID_TEST1, null, null)));

        ResponseEntity<FooResource> result = controller.getFoo(FOO_ID_TEST1);

        assertNotNull(result.getBody());
    }


    @Test
    public void testDelete() {
        controller.deleteFoo(FOO_ID_TEST1);
    }

    @Test
    public void testFindAll() {
        when(fooResourceAssembler.toResources(anyListOf(Foo.class))).thenReturn(new ArrayList<FooResource>());

        ResponseEntity<List<FooResource>> result = controller.getAll();

        assertNotNull(result.getBody());
    }

    private FooResource createFooResourceWithLink(Foo foo) {
        FooResource fooResource = new FooResource().setFoo(foo);
        fooResource.add(new Link("http://localhost:8080/api/foos/" + FOO_ID_TEST1));
        return fooResource;
    }
}
