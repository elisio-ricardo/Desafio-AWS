package com.elisio.desafioAWS.application.port.in;

import com.elisio.desafioAWS.framework.adapter.in.dtos.BookRequestDTO;
import com.elisio.desafioAWS.framework.adapter.in.dtos.BookResponseDTO;
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
