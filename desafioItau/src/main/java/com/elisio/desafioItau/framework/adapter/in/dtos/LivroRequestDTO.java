package com.elisio.desafioItau.framework.adapter.in.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LivroRequestDTO(
        @NotNull @NotEmpty String titulo,
        @NotNull @NotEmpty String autor,
        @NotNull Integer qtdPaginas,
        @NotNull Double preco
) {}
