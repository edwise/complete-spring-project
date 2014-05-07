package com.edwise.completespring;

import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.SequenceId;
import com.edwise.completespring.repositories.BookRepository;
import com.edwise.completespring.repositories.SequenceIdRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit test for simple Application.
 */
public class ApplicationTest {

    private Application application;

    @Mock
    BookRepository bookRepository;

    @Mock
    SequenceIdRepository sequenceRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        application = new Application();
        ReflectionTestUtils.setField(this.application, "bookRepository", this.bookRepository);
        ReflectionTestUtils.setField(this.application, "sequenceRepository", this.sequenceRepository);
    }

    @Test
    public void testRun() throws Exception {
        application.run("");
        verify(sequenceRepository, times(1)).save(any(SequenceId.class));
        verify(bookRepository, times(1)).deleteAll();
        verify(bookRepository, times(4)).save(any(Book.class)); // Número de libros insertados de ejemplo al arrancar: 4
    }
}
