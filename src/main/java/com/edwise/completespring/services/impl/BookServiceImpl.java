package com.edwise.completespring.services.impl;

import com.edwise.completespring.entities.Book;
import com.edwise.completespring.exceptions.NotFoundException;
import com.edwise.completespring.repositories.BookRepository;
import com.edwise.completespring.repositories.SequenceIdRepository;
import com.edwise.completespring.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    public static final String BOOK_COLLECTION = "books";
    private static final String BOOK_NOT_FOUND_MSG = "Book not found";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SequenceIdRepository sequenceIdRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book findOne(Long id) {
        Optional<Book> result = bookRepository.findById(id);
        if (!result.isPresent()) {
            throw new NotFoundException(BOOK_NOT_FOUND_MSG);
        }
        return result.get();
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book create(Book book) {
        Long id = sequenceIdRepository.getNextSequenceId(BOOK_COLLECTION);
        book.setId(id);

        return bookRepository.save(book);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> findByReleaseDate(LocalDate releaseDate) {
        return bookRepository.findByReleaseDate(releaseDate);
    }
}
