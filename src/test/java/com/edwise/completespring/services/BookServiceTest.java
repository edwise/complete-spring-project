package com.edwise.completespring.services;

import com.edwise.completespring.entities.AuthorTest;
import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.PublisherTest;
import com.edwise.completespring.exceptions.NotFoundException;
import com.edwise.completespring.repositories.BookRepository;
import com.edwise.completespring.repositories.SequenceIdRepository;
import com.edwise.completespring.services.impl.BookServiceImpl;
import com.edwise.completespring.testutil.BookBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    private static final long BOOK_ID_TEST1 = 3L;
    private static final long BOOK_ID_TEST2 = 400L;
    private static final long BOOK_ID_TEST3 = 401L;
    private static final String BOOK_TITLE_TEST1 = "Lord of the Rings";
    private static final String BOOK_TITLE_TEST2 = "La espada del destino";
    private static final String BOOK_ISBN_TEST1 = "11-333-12";
    private static final String BOOK_ISBN_TEST2 = "12-1234-12";
    private static final String PUBLISHER_NAME_TEST1 = "Editorial Alfaguara";
    private static final String PUBLISHER_NAME_TEST2 = "Gigamesh";
    private static final String PUBLISHER_COUNTRY_TEST1 = "ES";
    private static final String PUBLISHER_COUNTRY_TEST2 = "US";
    private static final String AUTHOR_NAME_TEST1 = "Stephen";
    private static final String AUTHOR_NAME_TEST2 = "William";
    private static final String AUTHOR_SURNAME_TEST1 = "King";
    private static final String AUTHOR_SURNAME_TEST2 = "Shakespeare";
    private static final LocalDate BOOK_RELEASEDATE_TEST1 = LocalDate.of(2013, 1, 26);
    private static final LocalDate BOOK_RELEASEDATE_TEST2 = LocalDate.of(2011, 11, 12);
    private static final int ONE_TIME = 1;
    private static final int TWO_ITEMS = 2;

    @Mock
    BookRepository bookRepository;

    @Mock
    SequenceIdRepository sequenceIdRepository;

    @InjectMocks
    private BookService service = new BookServiceImpl();

    @Test
    public void testFindAll() {
        List<Book> books = createTestBookList();
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = service.findAll();

        verify(bookRepository, times(ONE_TIME)).findAll();
        assertEquals("2 elements in page", TWO_ITEMS, result.size());
    }

    @Test
    public void testDelete() {
        doNothing().when(bookRepository).deleteById(BOOK_ID_TEST1);

        service.delete(BOOK_ID_TEST1);

        verify(bookRepository, times(ONE_TIME)).deleteById(BOOK_ID_TEST1);
    }

    @Test
    public void testFindOne() {
        when(bookRepository.findById(BOOK_ID_TEST1)).thenReturn(Optional.of(new Book().setId(BOOK_ID_TEST1)));

        Book result = service.findOne(BOOK_ID_TEST1);

        verify(bookRepository, timeout(ONE_TIME)).findById(BOOK_ID_TEST1);
        assertEquals(Long.valueOf(BOOK_ID_TEST1), result.getId());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFound() {
        when(bookRepository.findById(BOOK_ID_TEST1)).thenReturn(Optional.empty());

        service.findOne(BOOK_ID_TEST1);
    }

    @Test
    public void testSave() {
        Book foo = new BookBuilder().title(BOOK_TITLE_TEST2).build();
        Book emptyFoo = new BookBuilder().build();
        Book dbBook = emptyFoo.copyFrom(foo).setId(BOOK_ID_TEST1);
        when(bookRepository.save(foo)).thenReturn(dbBook);

        Book saved = service.save(foo);

        assertEquals(dbBook, saved);
    }

    @Test
    public void testCreate() {
        Book foo = new BookBuilder().title(BOOK_TITLE_TEST2).build();
        Book emptyFoo = new BookBuilder().build();
        Book dbBook = emptyFoo.copyFrom(foo).setId(BOOK_ID_TEST1);
        when(sequenceIdRepository.getNextSequenceId(BookServiceImpl.BOOK_COLLECTION)).thenReturn(BOOK_ID_TEST3);
        when(bookRepository.save(foo)).thenReturn(dbBook.setId(BOOK_ID_TEST3));

        Book saved = service.create(foo);

        assertEquals(dbBook, saved);
        assertEquals(Long.valueOf(BOOK_ID_TEST3), saved.getId());
    }

    @Test
    public void testFindByTitle() {
        List<Book> bookResults =
                Collections.singletonList(new BookBuilder().id(BOOK_ID_TEST1).title(BOOK_TITLE_TEST1).build());
        when(bookRepository.findByTitle(BOOK_TITLE_TEST1)).thenReturn(bookResults);

        List<Book> result = service.findByTitle(BOOK_TITLE_TEST1);

        verify(bookRepository, timeout(ONE_TIME)).findByTitle(BOOK_TITLE_TEST1);
        assertEquals(ONE_TIME, result.size());
    }

    @Test
    public void testFindByReleaseDate() {
        List<Book> bookResults =
                Collections.singletonList(new BookBuilder().id(BOOK_ID_TEST1).releaseDate(BOOK_RELEASEDATE_TEST1).build());
        when(bookRepository.findByReleaseDate(BOOK_RELEASEDATE_TEST1)).thenReturn(bookResults);

        List<Book> result = service.findByReleaseDate(BOOK_RELEASEDATE_TEST1);

        verify(bookRepository, timeout(ONE_TIME)).findByReleaseDate(BOOK_RELEASEDATE_TEST1);
        assertEquals(ONE_TIME, result.size());
    }

    private List<Book> createTestBookList() {
        Book book1 = new BookBuilder()
                .id(BOOK_ID_TEST1)
                .title(BOOK_TITLE_TEST1)
                .authors(Collections.singletonList(AuthorTest.createAuthor(AUTHOR_NAME_TEST1, AUTHOR_SURNAME_TEST1)))
                .isbn(BOOK_ISBN_TEST1)
                .releaseDate(BOOK_RELEASEDATE_TEST1)
                .publisher(PublisherTest.createPublisher(PUBLISHER_NAME_TEST1, PUBLISHER_COUNTRY_TEST1, false))
                .build();
        Book book2 = new BookBuilder()
                .id(BOOK_ID_TEST2)
                .title(BOOK_TITLE_TEST2)
                .authors(Collections.singletonList(AuthorTest.createAuthor(AUTHOR_NAME_TEST2, AUTHOR_SURNAME_TEST2)))
                .isbn(BOOK_ISBN_TEST2)
                .releaseDate(BOOK_RELEASEDATE_TEST2)
                .publisher(PublisherTest.createPublisher(PUBLISHER_NAME_TEST2, PUBLISHER_COUNTRY_TEST2, true))
                .build();

        return Arrays.asList(book1, book2);
    }

}
