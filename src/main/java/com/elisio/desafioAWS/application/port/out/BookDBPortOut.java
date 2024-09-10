package com.elisio.desafioAWS.application.port.out;

import com.elisio.desafioAWS.domain.entities.Book;

import java.util.List;

public interface BookDBPortOut {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book saveBook(Book book);

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);
}
