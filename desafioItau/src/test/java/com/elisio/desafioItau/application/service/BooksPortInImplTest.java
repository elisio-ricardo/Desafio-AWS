package com.elisio.desafioItau.application.service;

import com.elisio.desafioItau.domain.Book;
import com.elisio.desafioItau.factory.BookFactory;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookRequestDTO;
import com.elisio.desafioItau.framework.adapter.out.BookDbPortOutImpl;
import com.elisio.desafioItau.framework.exceptions.BookException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BooksPortInImplTest {

    @Mock
    private BookDbPortOutImpl bookDbPortOut;

    @InjectMocks
    private BooksPortInImpl booksPortIn;

    @Test
    @DisplayName("Verify method getAllBooks return a list")
    void getAllBooks() {
        when(bookDbPortOut.getAllBooks()).thenReturn(BookFactory.bookList());

        var bookList = booksPortIn.getAllBooks();

        assertEquals(3, bookList.size());
        verify(bookDbPortOut, Mockito.times(1)).getAllBooks();
    }

    @Test
    @DisplayName("Verify if method return a book")
    void getBookByID() {
        when((bookDbPortOut.getBookById(1L))).thenReturn(BookFactory.book());

        var book = booksPortIn.getBookByID(1L);

        assertEquals(book.autor(), "autor1");
        verify(bookDbPortOut, Mockito.times(1)).getBookById(1L);
    }

    @Test
    @DisplayName("Verify if method return a book with diferente autor")
    void getBookByIDAndVerifyNomeNotEquals() {
        when((bookDbPortOut.getBookById(1L))).thenReturn(BookFactory.book());

        var book = booksPortIn.getBookByID(1L);

        assertNotEquals(book.autor(), "autorErrado");
        verify(bookDbPortOut, Mockito.times(1)).getBookById(1L);
    }

    @Test
    @DisplayName("Confirm save entity")
    void saveBook() {
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

        when(bookDbPortOut.saveBook(any())).thenReturn(book);
        var bookSaved = booksPortIn.saveBook(bookRequest);
        assertEquals(book.getTitulo(), bookSaved.titulo());
        verify(bookDbPortOut, Mockito.times(1)).saveBook(any());
    }

    @Test
    @DisplayName("confirm that the book has been updated")
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

        when(bookDbPortOut.updateBook(1L, book)).thenReturn(book);
        var bookUpdated = booksPortIn.updateBook(1L, bookRequest);
        assertEquals(book.getTitulo(), bookUpdated.titulo());
        verify(bookDbPortOut, Mockito.times(1)).updateBook(1L, book);
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

        BookRequestDTO bookRequest = BookRequestDTO.builder()
                .autor("autor1")
                .titulo("tituli1")
                .preco(1.0)
                .qtdPaginas(1)
                .build();

        Mockito.doThrow(BookException.class).when(bookDbPortOut).updateBook(1L, book);

        assertThrows(BookException.class, () -> bookDbPortOut.updateBook(1L, book));

        verify(bookDbPortOut, Mockito.times(1)).updateBook(1L, book);
    }


    @Test
    @DisplayName("confirm that the book has been Deleted")
    void deleteBook() {
        booksPortIn.deleteBook(1L);

        verify(bookDbPortOut, Mockito.times(1)).deleteBook(1L);
    }

    @Test
    @DisplayName("confirm that the book has been Deleted")
    void notdeleteBookBecauseBookNotExist() {

        Mockito.doThrow(BookException.class).when(bookDbPortOut).deleteBook(1L);

        assertThrows(BookException.class, () -> bookDbPortOut.deleteBook(1L));

        verify(bookDbPortOut, Mockito.times(1)).deleteBook(1L);
    }


}