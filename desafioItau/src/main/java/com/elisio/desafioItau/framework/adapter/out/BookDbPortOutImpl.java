package com.elisio.desafioItau.framework.adapter.out;

import com.elisio.desafioItau.application.port.out.LivroDBPortOut;
import com.elisio.desafioItau.domain.Book;
import com.elisio.desafioItau.framework.adapter.out.persistence.LivroRepository;
import com.elisio.desafioItau.framework.exceptions.LivroException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BookDbPortOutImpl implements LivroDBPortOut {

    private final LivroRepository livroRepository;

    public BookDbPortOutImpl(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }


    @Override
    public List<Book> getAllBooks() {
        return livroRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return livroRepository.findById(id).orElseThrow(() -> new LivroException("Book not founded " + id));
    }

    @Override
    public Book saveBook(Book book) {
        return livroRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        if (!livroRepository.existsById(id)) {
            throw new LivroException("Book not founded to update: " + id);
        }

        book.setId(id);
        return livroRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new LivroException("Book not founded to update: " + id);
        }

        livroRepository.deleteById(id);
    }
}
