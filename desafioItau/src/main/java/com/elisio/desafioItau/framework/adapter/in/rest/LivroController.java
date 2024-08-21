package com.elisio.desafioItau.framework.adapter.in.rest;


import com.elisio.desafioItau.application.port.in.BooksPortIn;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookRequestDTO;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookResponseDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/books")
@Tag(name = "Books REST")
public class LivroController {

    private final BooksPortIn booksPortIn;


    public LivroController(BooksPortIn booksPortIn) {
        this.booksPortIn = booksPortIn;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAll() {
        return ResponseEntity.ok(booksPortIn.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(booksPortIn.getBookByID(id));
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> saveBook(@RequestBody @Valid BookRequestDTO bookRequestDTO) {
        BookResponseDTO book = booksPortIn.saveBook(bookRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(book.id())
                .toUri();

        return ResponseEntity.created(uri).body(book);
    }


    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable("id") Long id,
                                                      @RequestBody @Valid BookRequestDTO bookRequestDTO) {
        return ResponseEntity.ok(booksPortIn.updateBook(id, bookRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        booksPortIn.deleteBook(id);
        return ResponseEntity.noContent().build();
    }




}
