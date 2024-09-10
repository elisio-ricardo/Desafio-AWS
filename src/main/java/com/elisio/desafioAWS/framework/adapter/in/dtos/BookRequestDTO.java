package com.elisio.desafioAWS.framework.adapter.in.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record BookRequestDTO(
        @NotNull @NotEmpty String autor,

        @NotNull @NotEmpty String titulo,

        @NotNull Integer qtdPaginas,
        @NotNull Double preco

) {}
