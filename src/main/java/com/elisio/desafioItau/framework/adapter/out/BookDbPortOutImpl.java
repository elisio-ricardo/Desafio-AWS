package com.elisio.desafioItau.framework.adapter.out;

import com.elisio.desafioItau.application.port.out.BookDBPortOut;
import com.elisio.desafioItau.domain.entity.Book;
import com.elisio.desafioItau.framework.adapter.out.persistence.BookRepository;
import com.elisio.desafioItau.framework.exceptions.BookException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BookDbPortOutImpl implements BookDBPortOut {

    private final BookRepository bookRepository;

    public BookDbPortOutImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookException("Book not founded " + id));
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        if (!bookRepository.existsById(id)) {
            throw new BookException("Book not founded to update: " + id);
        }

        book.setId(id);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookException("Book not founded to update: " + id);
        }

        bookRepository.deleteById(id);
    }
}
