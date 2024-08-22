package com.elisio.desafioItau.application.port.in;

import com.elisio.desafioItau.framework.adapter.in.dtos.BookRequestDTO;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BooksPortIn {

    List<BookResponseDTO> getAllBooks();

    BookResponseDTO getBookByID(Long id);

    BookResponseDTO saveBook(BookRequestDTO bookRequestDTO);

    BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO);

    void deleteBook(Long id);

}
