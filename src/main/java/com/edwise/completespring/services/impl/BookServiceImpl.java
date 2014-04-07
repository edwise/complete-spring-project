package com.edwise.completespring.services.impl;

import com.edwise.completespring.entities.Book;
import com.edwise.completespring.exceptions.NotFoundException;
import com.edwise.completespring.repositories.BookRepository;
import com.edwise.completespring.repositories.SequenceIdRepository;
import com.edwise.completespring.services.BookService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user EAnton on 07/04/2014.
 */
@Service
public class BookServiceImpl implements BookService {

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
        Book result = bookRepository.findOne(id);
        if (result == null) {
            throw new NotFoundException("No existe la entidad");
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        bookRepository.delete(id);
    }

    @Override
    public Book create(Book book) {
        Long id = sequenceIdRepository.getNextSequenceId(BookRepository.BOOK_COLLECTION);
        book.setId(id);

        return bookRepository.save(book);
    }

    @Override
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> findByReleaseDate(LocalDate releaseDate) {
        return bookRepository.findByReleaseDate(releaseDate);
    }
}
