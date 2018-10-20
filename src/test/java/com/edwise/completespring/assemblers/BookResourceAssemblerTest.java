package com.edwise.completespring.assemblers;

import com.edwise.completespring.entities.Book;
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
public class BookResourceAssemblerTest {
    private static final long BOOK_ID_TEST = 1234L;

    @Mock
    private Book book;

    private BookResourceAssembler bookResourceAssembler = new BookResourceAssembler();

    @Test
    public void testInstantiateResource() {
        BookResource bookResource = bookResourceAssembler.instantiateResource(book);

        assertThat(bookResource).isNotNull();
        assertThat(bookResource.getBook()).isEqualTo(book);
    }

    @Test
    public void testToResource() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
        when(book.getId()).thenReturn(BOOK_ID_TEST);

        BookResource bookResource = bookResourceAssembler.toResource(book);

        assertThat(bookResource).isNotNull();
        assertThat(bookResource.getBook()).isEqualTo(book);
    }
}