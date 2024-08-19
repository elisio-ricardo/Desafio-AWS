package com.elisio.desafioItau.framework.adapter.in.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
public record BookResponseDTO(
        Long id,
        String titulo,
        String autor,
        Integer qtdPaginas,
        Double preco
) {
}