package com.elisio.desafioAWS.framework.adapter.out.aws.dto;

import com.elisio.desafioAWS.domain.entities.Book;
import com.elisio.desafioAWS.domain.enums.OperacaoEnum;
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
