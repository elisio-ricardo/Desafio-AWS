package com.elisio.desafioItau.factory;

import com.elisio.desafioItau.domain.entities.Book;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookRequestDTO;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class BookFactory {


    public static List<Book> bookList() {

        Book book1 = Book.builder()
                .autor("autor1")
                .titulo("titulo1")
                .preco(1.0)
                .qtdPaginas(1)
                .build();

        Book book2 = Book.builder()
                .autor("autor2")
                .titulo("titulo2")
                .preco(2.0)
                .qtdPaginas(2)
                .build();
        Book book3 = Book.builder()
                .autor("autor3")
                .titulo("titulo3")
                .preco(3.0)
                .qtdPaginas(3)
                .build();

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);

        return bookList;

    }


    public static List<BookResponseDTO> bookListResponseDTO() {

        BookResponseDTO book1 = BookResponseDTO.builder()
                .autor("autor1")
                .titulo("titulo1")
                .preco(1.0)
                .qtdPaginas(1)
                .build();

        BookResponseDTO book2 = BookResponseDTO.builder()
                .autor("autor2")
                .titulo("titulo2")
                .preco(2.0)
                .qtdPaginas(2)
                .build();
        BookResponseDTO book3 = BookResponseDTO.builder()
                .autor("autor3")
                .titulo("tituli3")
                .preco(3.0)
                .qtdPaginas(3)
                .build();

        List<BookResponseDTO> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);

        return bookList;

    }

    public static Book book() {
        Book book = Book.builder()
                .autor("autor1")
                .titulo("titulo1")
                .preco(1.0)
                .qtdPaginas(1)
                .build();

        return book;
    }

    public static BookResponseDTO bookResponseDTO() {
        BookResponseDTO book = BookResponseDTO.builder()
                .autor("autor1")
                .titulo("titulo1")
                .preco(1.0)
                .qtdPaginas(1)
                .build();

        return book;
    }

    public static BookRequestDTO bookRequestDTO() {
        BookRequestDTO book = BookRequestDTO.builder()
                .autor("autor1")
                .titulo("titulo1")
                .preco(1.0)
                .qtdPaginas(1)
                .build();

        return book;
    }

}
