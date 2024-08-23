package com.elisio.desafioItau.framework.adapter.out.aws.dto;

import com.elisio.desafioItau.domain.entity.Book;
import com.elisio.desafioItau.domain.enums.OperacaoEnum;
import lombok.Data;

@Data
public class BookResponseSNSDTO {
    private String autor;
    private String titulo;
    private Integer qtdPaginas;

    private Double preco;

    private String operacao;


    public BookResponseSNSDTO(Book book, OperacaoEnum operacao) {
        this.autor = book.getAutor();
        this.titulo = book.getTitulo();
        this.qtdPaginas = book.getQtdPaginas();
        this.preco = book.getPreco();
        this.operacao = operacao.toString();
    }
}
