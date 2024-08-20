package com.elisio.desafioItau.framework.adapter.out;

import com.elisio.desafioItau.domain.Book;
import com.elisio.desafioItau.factory.BookFactory;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookRequestDTO;
import com.elisio.desafioItau.framework.adapter.out.persistence.BookRepository;
import com.elisio.desafioItau.framework.exceptions.BookException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookDbPortOutImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookDbPortOutImpl bookDbPortOutImpl;

    @Test
    @DisplayName("Return all books")
    void getAllBooks() {
        when(bookRepository.findAll()).thenReturn(BookFactory.bookList());

        var bookList = bookDbPortOutImpl.getAllBooks();

        assertEquals(3, bookList.size());
        verify(bookRepository, Mockito.times(1)).findAll();

    }

    @Test
    @DisplayName("Return a book by id")
    void getBookById() {
        when((bookRepository.findById(1L))).thenReturn(Optional.ofNullable(BookFactory.book()));

        var book = bookDbPortOutImpl.getBookById(1L);

        assertEquals(book.getAutor(), "autor1");
        verify(bookRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Not Return a book by id")
    void getBookByIdNotReturnBook() {

        Mockito.doThrow(BookException.class).when(bookRepository).findById(1L);

        assertThrows(BookException.class, () -> bookDbPortOutImpl.getBookById(1L));

        verify(bookRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Save a Book")
    void saveBook() {
        Book book = Book.builder()
                .autor("autor1")
                .titulo("tituli1")
                .preco(1.0)
                .qtdPaginas(1)
                .build();

        when((bookRepository.save(any()))).thenReturn(BookFactory.book());

        var bookSaved = bookDbPortOutImpl.saveBook(book);

        assertEquals(bookSaved.getAutor(), "autor1");
        verify(bookRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Update a Book")
    void updateBook() {

        Book book = Book.builder()
                .autor("autor1")
                .titulo("tituli1")
                .preco(1.0)
                .qtdPaginas(1)
                .build();

        BookRequestDTO bookRequest = BookRequestDTO.builder()
                .autor("autor1")
                .titulo("tituli1")
                .preco(1.0)
                .qtdPaginas(1)
                .build();

        when(bookRepository.existsById(any())).thenReturn(true);
        when(bookRepository.save(book)).thenReturn(book);
        var bookUpdated = bookDbPortOutImpl.updateBook(1L, book);

        assertEquals( "autor1", bookUpdated.getAutor() );
        verify(bookRepository, Mockito.times(1)).save(book);
    }

    @Test
    @DisplayName("When book not exist return throw")
    void tryUpdateBookButBookNotExist() {
        Book book = Book.builder()
                .autor("autor1")
                .titulo("tituli1")
                .preco(1.0)
                .qtdPaginas(1)
                .build();

        when(bookRepository.existsById(any())).thenReturn(false);

        assertThrows(BookException.class, () -> bookDbPortOutImpl.updateBook(1L, book));

        verify(bookRepository, Mockito.times(1)).existsById(1L);
    }



    @Test
    @DisplayName("confirm that the book has been Deleted")
    void deleteBook() {

        when(bookRepository.existsById(any())).thenReturn(true);
        bookDbPortOutImpl.deleteBook(1L);

        verify(bookRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Can't delete book")
    void NotdeleteBookBecauseNotExist() {

        when(bookRepository.existsById(any())).thenReturn(false);
        assertThrows(BookException.class, () -> bookDbPortOutImpl.deleteBook(1L));

        verify(bookRepository, Mockito.times(0)).deleteById(1L);
    }
}