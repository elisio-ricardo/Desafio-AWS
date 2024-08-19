package com.elisio.desafioItau.application.port.in;

import com.elisio.desafioItau.framework.adapter.in.dtos.LivroRequestDTO;
import com.elisio.desafioItau.framework.adapter.in.dtos.LivroResponseDTO;

import java.util.List;

public interface LivrosPortIn {

    List<LivroResponseDTO> getAllLivros();

    LivroResponseDTO getLivroByID(Long id);

    LivroResponseDTO saveLivro(LivroRequestDTO livroRequestDTO);

    LivroResponseDTO updateLivro(Long id, LivroRequestDTO livroRequestDTO);

    void deleteLivro(Long id);

}
