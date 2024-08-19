package com.elisio.desafioItau.application.port.out;

import com.elisio.desafioItau.domain.Livro;

import java.util.List;

public interface LivroDBPortOut {

    List<Livro> getAllLivros();

    Livro getById(Long id);

    Livro saveLivro(Livro livro);

    Livro updateLivro(Long id, Livro livro);

    void deleteLivro(Long id);
}
