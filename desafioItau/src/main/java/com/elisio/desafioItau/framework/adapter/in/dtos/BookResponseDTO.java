package com.elisio.desafioItau.framework.adapter.in.dtos;

import lombok.Builder;

@Builder
public record BookResponseDTO(
        Long id,
        String titulo,
        String autor,
        Integer qtdPaginas,
        Double preco
) {
}