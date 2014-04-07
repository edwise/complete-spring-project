package com.edwise.completespring.services;

import com.edwise.completespring.entities.Book;
import com.edwise.completespring.exceptions.NotFoundException;
import com.edwise.completespring.repositories.BookRepository;
import com.edwise.completespring.repositories.SequenceIdRepository;
import com.edwise.completespring.services.impl.BookServiceImpl;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by user EAnton on 07/04/2014.
 */
public class BookServiceTest {

    BookService service;

    @Mock
    BookRepository bookRepository;

    @Mock
    SequenceIdRepository sequenceIdRepository;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new BookServiceImpl();
        ReflectionTestUtils.setField(this.service, "bookRepository", this.bookRepository); // inject bookRepository
        ReflectionTestUtils.setField(this.service, "sequenceIdRepository", this.sequenceIdRepository); // inject sequenceIdRepository
    }

    @Test
    public void testFindAll() {
        List books = Arrays.asList(new Book(3l, "Libro prueba", Arrays.asList("Edu"), "11-333-12", new LocalDate()),
                new Book(400l, "Libro prueba 2", Arrays.asList("Otro", "S. King"), "12-1234-12", new LocalDate()),
                new Book(14l, "Libro prueba 3", Arrays.asList("Nadie"), "12-9999-92", new LocalDate()));

        when(bookRepository.findAll()).thenReturn(books);
        List<Book> result = service.findAll();
        verify(bookRepository, timeout(1)).findAll();
        assertEquals("3 elements in page", 3, result.size());
    }

    @Test
    public void testDelete() {
        long id = 1l;
        doNothing().when(bookRepository).delete(id);
        service.delete(id);
        verify(bookRepository, times(1)).delete(id);
    }

    @Test
    public void testFindOne() {
        when(bookRepository.findOne(1l)).thenReturn(new Book().setId(1l));
        Book result = service.findOne(1l);
        verify(bookRepository, timeout(1)).findOne(1l);
        assertEquals("same Id", Long.valueOf(1l), result.getId());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFound() {
        when(bookRepository.findOne(1l)).thenReturn(null);
        service.findOne(1l);
    }

    @Test
    public void testSave() {
        Book foo = new Book().setTitle("sample Text");
        Book dbBook = new Book().copyFrom(foo).setId(1l);
        when(bookRepository.save(foo)).thenReturn(dbBook);
        Book saved = service.save(foo);
        assertEquals(dbBook, saved);
    }

    @Test
    public void testCreate() {
        Book foo = new Book().setTitle("sample Text");
        Book dbBook = new Book().copyFrom(foo).setId(1l);
        when(sequenceIdRepository.getNextSequenceId(BookRepository.BOOK_COLLECTION)).thenReturn(5l);
        when(bookRepository.save(foo)).thenReturn(dbBook.setId(5l));
        Book saved = service.create(foo);
        assertEquals(dbBook, saved);
        assertEquals(saved.getId(), Long.valueOf(5l));
    }

    @Test
    public void testFindByTitle() {
        String comunTitle = "Titulo igual";
        List books = Arrays.asList(new Book(3l, comunTitle, Arrays.asList("Edu"), "11-333-12", new LocalDate()),
                new Book(14l, comunTitle, Arrays.asList("Nadie"), "12-9999-92", new LocalDate()));

        when(bookRepository.findByTitle(comunTitle)).thenReturn(books);
        List<Book> result = service.findByTitle(comunTitle);
        verify(bookRepository, timeout(1)).findByTitle(comunTitle);
        assertEquals("2 elements in page", 2, result.size());
    }

    @Test
    public void testFindByReleaseDate() {
        LocalDate comunLocalDate = new LocalDate();
        List books = Arrays.asList(new Book(3l, "Libro prueba", Arrays.asList("Edu"), "11-333-12", comunLocalDate),
                new Book(14l, "Libro prueba 2", Arrays.asList("Nadie"), "12-9999-92", comunLocalDate));

        when(bookRepository.findByReleaseDate(comunLocalDate)).thenReturn(books);
        List<Book> result = service.findByReleaseDate(comunLocalDate);
        verify(bookRepository, timeout(1)).findByReleaseDate(comunLocalDate);
        assertEquals("2 elements in page", 2, result.size());
    }
}
