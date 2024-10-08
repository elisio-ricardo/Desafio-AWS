package com.elisio.desafioAWS.application.service;


import com.elisio.desafioAWS.application.port.in.BooksPortIn;
import com.elisio.desafioAWS.domain.entities.Book;
import com.elisio.desafioAWS.domain.enums.OperacaoEnum;
import com.elisio.desafioAWS.framework.adapter.in.dtos.BookRequestDTO;
import com.elisio.desafioAWS.framework.adapter.in.dtos.BookResponseDTO;
import com.elisio.desafioAWS.framework.adapter.out.BookDbPortOutImpl;
import com.elisio.desafioAWS.framework.adapter.out.aws.AwsSnsService;
import com.elisio.desafioAWS.framework.adapter.out.aws.dto.BookResponseSNSDTO;
import com.elisio.desafioAWS.framework.exceptions.SendAwsSNSException;
import com.elisio.desafioAWS.framework.util.mapper.BookMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BooksPortInImpl implements BooksPortIn {

    private final BookDbPortOutImpl bookDbPortOut;
    private final AwsSnsService awsSnsService;
    private final ObjectMapper mapper;

    public BooksPortInImpl(BookDbPortOutImpl bookDbPortOut, AwsSnsService awsSnsService, ObjectMapper mapper) {
        this.bookDbPortOut = bookDbPortOut;
        this.awsSnsService = awsSnsService;
        this.mapper = mapper;
    }


    @Override
    public List<BookResponseDTO> getAllBooks() {
        log.info("Requesting all books");
        List<Book> allBook = bookDbPortOut.getAllBooks();
        log.info("Transform books in DTO and Returning all books");
        return allBook.stream().map(BookMapper::toBookResponseDTO).collect(Collectors.toList());
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
        BookResponseSNSDTO bookSNS = new BookResponseSNSDTO(book, OperacaoEnum.SAVE);
        String jsonString = null;

        try {
            jsonString = mapper.writeValueAsString(bookSNS);
            this.awsSnsService.publish(jsonString);
        } catch (JsonProcessingException e) {
            throw new SendAwsSNSException("Erro ao publicar ao SNS a mensagem");
        }

        log.info("SNS saveBook enviado mensagem " + jsonString);
        return BookMapper.toBookResponseDTO(book);
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO) {
        log.info("Update - book");
        Book book = bookDbPortOut.updateBook(id, BookMapper.toBook(bookRequestDTO));
        log.info("Book updated successfully");

        BookResponseSNSDTO bookSNS = new BookResponseSNSDTO(book, OperacaoEnum.UPDATE);
        String jsonString = null;

        try {
            jsonString = mapper.writeValueAsString(bookSNS);
            this.awsSnsService.publish(jsonString);
        } catch (JsonProcessingException e) {
            throw new SendAwsSNSException("Erro ao publicar ao SNS a mensagem");
        }

        log.info("SNS updateBook enviado com a mensagem " + jsonString);
        return BookMapper.toBookResponseDTO(book);
    }

    @Override
    public void deleteBook(Long id) {
        var book = bookDbPortOut.getBookById(id);
        log.info("DELETE - Deleting book");
        bookDbPortOut.deleteBook(id);
        log.info("Book deleted");
        BookResponseSNSDTO bookSNS = new BookResponseSNSDTO(book, OperacaoEnum.DELETE);
        String jsonString = null;

        try {
            jsonString = mapper.writeValueAsString(bookSNS);
            this.awsSnsService.publish(jsonString);
        } catch (JsonProcessingException e) {
            throw new SendAwsSNSException("Erro ao publicar ao SNS a mensagem");
        }

        log.info("SNS updateBook enviado com a mensagem " + jsonString);
    }
}
