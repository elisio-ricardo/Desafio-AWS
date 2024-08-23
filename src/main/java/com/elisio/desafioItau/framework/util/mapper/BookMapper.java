package com.elisio.desafioItau.framework.util.mapper;

import com.elisio.desafioItau.domain.entities.Book;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookRequestDTO;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookResponseDTO;

public class BookMapper {

    public static Book toBook(BookRequestDTO bookRequestDTO) {
        return Book
                .builder()
                .autor(bookRequestDTO.autor())
                .titulo(bookRequestDTO.titulo())
                .qtdPaginas(bookRequestDTO.qtdPaginas())
                .preco(bookRequestDTO.preco())
                .build();
    }

    public static BookResponseDTO toBookResponseDTO(Book book) {
        return BookResponseDTO
                .builder()
                .id(book.getId())
                .autor(book.getAutor())
                .titulo(book.getTitulo())
                .preco(book.getPreco())
                .qtdPaginas(book.getQtdPaginas())
                .build();
    }


}
