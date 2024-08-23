package com.elisio.desafioItau.application.service;

import com.elisio.desafioItau.domain.entities.Book;
import com.elisio.desafioItau.domain.enums.OperacaoEnum;
import com.elisio.desafioItau.factory.BookFactory;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookRequestDTO;
import com.elisio.desafioItau.framework.adapter.out.BookDbPortOutImpl;
import com.elisio.desafioItau.framework.adapter.out.aws.AwsSnsService;
import com.elisio.desafioItau.framework.adapter.out.aws.dto.BookResponseSNSDTO;
import com.elisio.desafioItau.framework.exceptions.BookException;
import com.elisio.desafioItau.framework.exceptions.SendAwsSNSException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Mock
    private AwsSnsService awsSnsService;

    @InjectMocks
    private BooksPortInImpl booksPortIn;

    @Mock
    private ObjectMapper mapper;

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
    @DisplayName("Json error when try save entity")
    void errorWhenSaveBookExcptionJson() {
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

        BookResponseSNSDTO bookSNS = new BookResponseSNSDTO(book, OperacaoEnum.SAVE);

        try {
            doThrow(JsonProcessingException.class).when(mapper).writeValueAsString(bookSNS);
        } catch (JsonProcessingException e) {
            throw new SendAwsSNSException("Erro ao publicar ao SNS a mensagem");
        }
        when(bookDbPortOut.saveBook(any())).thenReturn(book);

        var sendAwsSNSException = assertThrows(SendAwsSNSException.class, () -> {
            booksPortIn.saveBook(bookRequest);
        });

        assertEquals("Erro ao publicar ao SNS a mensagem", sendAwsSNSException.getMessage());

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
    @DisplayName("Json error when try updated")
    void jsonErrorWhenUpdateBook() {
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

        BookResponseSNSDTO bookSNS = new BookResponseSNSDTO(book, OperacaoEnum.UPDATE);

        try {
            doThrow(JsonProcessingException.class).when(mapper).writeValueAsString(bookSNS);
        } catch (JsonProcessingException e) {
            throw new SendAwsSNSException("Erro ao publicar ao SNS a mensagem");
        }
        when(bookDbPortOut.updateBook(1L, book)).thenReturn(book);

        var sendAwsSNSException = assertThrows(SendAwsSNSException.class, () -> {
            booksPortIn.updateBook(1L, bookRequest);
        });

        assertEquals("Erro ao publicar ao SNS a mensagem", sendAwsSNSException.getMessage());
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

        doThrow(BookException.class).when(bookDbPortOut).updateBook(1L, book);

        assertThrows(BookException.class, () -> bookDbPortOut.updateBook(1L, book));

        verify(bookDbPortOut, Mockito.times(1)).updateBook(1L, book);
    }


    @Test
    @DisplayName("confirm that the book has been Deleted")
    void deleteBook() {
        Book book = Book.builder()
                .autor("autor1")
                .titulo("tituli1")
                .preco(1.0)
                .qtdPaginas(1)
                .build();

        when(bookDbPortOut.getBookById(1L)).thenReturn(book);

        booksPortIn.deleteBook(1L);
        verify(bookDbPortOut, Mockito.times(1)).deleteBook(1L);
    }

    @Test
    @DisplayName("confirm that the book has been Deleted")
    void notdeleteBookBecauseBookNotExist() {

        doThrow(BookException.class).when(bookDbPortOut).deleteBook(1L);

        assertThrows(BookException.class, () -> bookDbPortOut.deleteBook(1L));

        verify(bookDbPortOut, Mockito.times(1)).deleteBook(1L);
    }

    @Test
    @DisplayName("Json Error when try delete")
    void jsonErrorWhendeleteBook() {
        Book book = Book.builder()
                .autor("autor1")
                .titulo("tituli1")
                .preco(1.0)
                .qtdPaginas(1)
                .build();

        BookResponseSNSDTO bookSNS = new BookResponseSNSDTO(book, OperacaoEnum.DELETE);

        try {
            doThrow(JsonProcessingException.class).when(mapper).writeValueAsString(bookSNS);
        } catch (JsonProcessingException e) {
            throw new SendAwsSNSException("Erro ao publicar ao SNS a mensagem");
        }
        when(bookDbPortOut.getBookById(1L)).thenReturn(book);

        var sendAwsSNSException = assertThrows(SendAwsSNSException.class, () -> {
            booksPortIn.deleteBook(1L);
        });

        assertEquals("Erro ao publicar ao SNS a mensagem", sendAwsSNSException.getMessage());

    }



}