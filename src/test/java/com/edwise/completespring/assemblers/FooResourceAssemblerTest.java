package com.edwise.completespring.assemblers;

import com.edwise.completespring.entities.Foo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FooResourceAssemblerTest {
    private static final long FOO_ID_TEST = 1234L;

    @Mock
    private Foo foo;

    private FooResourceAssembler fooResourceAssembler = new FooResourceAssembler();

    @Test
    public void testInstantiateResource() {
        FooResource fooResource = fooResourceAssembler.instantiateModel(foo);

        assertThat(fooResource).isNotNull();
        assertThat(fooResource.getFoo()).isEqualTo(foo);
    }

    @Test
    public void testToResource() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
        when(foo.getId()).thenReturn(FOO_ID_TEST);

        FooResource fooResource = fooResourceAssembler.toModel(foo);

        assertThat(fooResource).isNotNull();
        assertThat(fooResource.getFoo()).isEqualTo(foo);
    }
}