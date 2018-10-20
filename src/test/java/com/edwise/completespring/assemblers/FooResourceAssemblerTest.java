package com.edwise.completespring.assemblers;

import com.edwise.completespring.entities.Foo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FooResourceAssemblerTest {
    private static final long FOO_ID_TEST = 1234L;

    @Mock
    private Foo foo;

    private FooResourceAssembler fooResourceAssembler = new FooResourceAssembler();

    @Test
    public void testInstantiateResource() {
        FooResource fooResource = fooResourceAssembler.instantiateResource(foo);

        assertThat(fooResource).isNotNull();
        assertThat(fooResource.getFoo()).isEqualTo(foo);
    }

    @Test
    public void testToResource() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
        when(foo.getId()).thenReturn(FOO_ID_TEST);

        FooResource fooResource = fooResourceAssembler.toResource(foo);

        assertThat(fooResource).isNotNull();
        assertThat(fooResource.getFoo()).isEqualTo(foo);
    }
}