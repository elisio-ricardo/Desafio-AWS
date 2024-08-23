package com.elisio.desafioItau.framework.adapter.in.rest;

import com.elisio.desafioItau.application.port.in.BooksPortIn;
import com.elisio.desafioItau.factory.BookFactory;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookRequestDTO;
import com.elisio.desafioItau.framework.adapter.in.dtos.BookResponseDTO;
import com.elisio.desafioItau.framework.exceptions.BookException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LivroController.class)
class LivroControllerTest {


    @MockBean
    private BooksPortIn booksPortIn;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }


    @Test
    @DisplayName("Return a list of books")
    void getAll() throws Exception {

        List<BookResponseDTO> bookList = new ArrayList<>();
        bookList.addAll(BookFactory.bookListResponseDTO());

        when(booksPortIn.getAllBooks()).thenReturn(bookList);


        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("titulo1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].autor").value("autor1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].titulo").value("titulo2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].autor").value("autor2"));
    }

    @Test
    @DisplayName("Return a Book by Id")
    void getBookById() throws Exception {

        when(booksPortIn.getBookByID(1L)).thenReturn(BookFactory.bookResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", 1L)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("titulo1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.autor").value("autor1"));
    }


    @Test
    @DisplayName("Save book")
    void saveBook() throws Exception {

        var bookRequest = BookFactory.bookRequestDTO();
        when(booksPortIn.saveBook(any(BookRequestDTO.class))).thenReturn(BookFactory.bookResponseDTO());


        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\": \"titulo1\", \"autor\": \"autor1\", \"qtdPaginas\": 1, \"preco\": 1.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("titulo1"))
                .andExpect(jsonPath("$.autor").value("autor1"))
                .andExpect(jsonPath("$.qtdPaginas").value(1))
                .andExpect(jsonPath("$.preco").value(1));
    }


    @Test
    @DisplayName("Update book")
    void updateBook() throws Exception {
        Long bookId = 1L;

        BookResponseDTO updatedBookResponseDTO = new BookResponseDTO(bookId, "titulo1", "autor1", 1, 1.0);
        String updatedBookJson = "{\"titulo\": \"titulo1\", \"autor\": \"autor1\", \"qtdPaginas\": 1, \"preco\": 1.0}";

        when(booksPortIn.updateBook(eq(bookId), any(BookRequestDTO.class))).thenReturn(updatedBookResponseDTO);

        mockMvc.perform(put("/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("titulo1"))
                .andExpect(jsonPath("$.autor").value("autor1"))
                .andExpect(jsonPath("$.qtdPaginas").value(1))
                .andExpect(jsonPath("$.preco").value(1));
    }

    @Test
    @DisplayName("Update but book not exist return bad Request")
    void updateBookWhenBookNotFoundShouldReturnBadRequest() throws Exception {
        Long bookId = 1L;

        String bookUpdateRequest = "{\"titulo\": \"titulo1\", \"autor\": \"autor1\", \"qtdPaginas\": 1, \"preco\": 1.0}";

        doThrow(new BookException("Book not found")).when(booksPortIn).updateBook(eq(bookId), any(BookRequestDTO.class));

        mockMvc.perform(put("/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookUpdateRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Book not found"));
    }

    @Test
    @DisplayName("Delete a book")
    void deleteBook()  throws Exception {
        Long bookId = 1L;
        doNothing().when(booksPortIn).deleteBook(bookId);

        mockMvc.perform(delete("/books/{id}", bookId))
                .andExpect(status().isNoContent());
    }
}