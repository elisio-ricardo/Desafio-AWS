package com.elisio.desafioItau.application.service;


import com.elisio.desafioItau.application.port.in.BooksPortIn;
import com.elisio.desafioItau.domain.Book;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookRequestDTO;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookResponseDTO;
import com.elisio.desafioItau.framework.adapter.out.BookDbPortOutImpl;
import com.elisio.desafioItau.framework.adapter.out.aws.AwsSnsService;
import com.elisio.desafioItau.framework.adapter.out.aws.MessageDTO;
import com.elisio.desafioItau.framework.util.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BooksPortInImpl implements BooksPortIn {

    private final BookDbPortOutImpl bookDbPortOut;

    private final AwsSnsService awsSnsService;


    public BooksPortInImpl(BookDbPortOutImpl bookDbPortOut, AwsSnsService awsSnsService) {
        this.bookDbPortOut = bookDbPortOut;
        this.awsSnsService = awsSnsService;
    }


    @Override
    public List<BookResponseDTO> getAllBooks() {
        log.info("Requesting all books");
        List<Book> allBook = bookDbPortOut.getAllBooks();
        log.info("Transform books in DTO and Returning all books");

        return allBook.stream().map(BookMapper::toBookResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO getBookByID(Long id) {
        log.info("GET - find book by ID");
        Book book = bookDbPortOut.getBookById(id);
        log.info("Book found successfully");
        return BookMapper.toBookResponseDTO(book);
    }

    @Override
    public BookResponseDTO saveBook(BookRequestDTO bookRequestDTO) {
        log.info("POST - Persisting book in BD");
        Book book = bookDbPortOut.saveBook(BookMapper.toBook(bookRequestDTO));
        log.info("Book saved");

        String operacao = ", \"operação\": \"save\"";
        String mensagemModificada = adicionarOperacaoNaMensagem(book.toString(), operacao);

        this.awsSnsService.publish(new MessageDTO(mensagemModificada));
        log.info("SNS saveBook enviado");

        return BookMapper.toBookResponseDTO(book);
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO) {
        log.info("Update - book");
        Book book = bookDbPortOut.updateBook(id, BookMapper.toBook(bookRequestDTO));
        log.info("Book updated successfully");

        String operacao = ", \"operação\": \"update\"";

        String mensagemModificada = adicionarOperacaoNaMensagem(book.toString(), operacao);

        this.awsSnsService.publish(new MessageDTO(book.toString()));

        log.info("SNS updateBook enviado");

        return BookMapper.toBookResponseDTO(book);
    }

    @Override
    public void deleteBook(Long id) {
       var  book = bookDbPortOut.getBookById(id);
        log.info("DELETE - Deleting book");
        bookDbPortOut.deleteBook(id);
        log.info("Book deleted");
//
//        String mensagem = "Book with id {0} deleted";
//        String mensagemFormatada = MessageFormat.format(mensagem, id);
//
//        log.info("SNS delete Book enviado" + mensagemFormatada);

        String operacao = ", \"operação\": \"delete\"";

        String mensagemModificada = adicionarOperacaoNaMensagem(book.toString(), operacao);

        this.awsSnsService.publish(new MessageDTO(mensagemModificada));
    }


    public static String adicionarOperacaoNaMensagem(String jsonString, String operacao) {
        if (jsonString.endsWith("}")) {
            String jsonSemFechamento = jsonString.substring(0, jsonString.length() - 1);
            return jsonSemFechamento + operacao + "}";
        } else {
            return jsonString + operacao;
        }
    }
}
