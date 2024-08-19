package com.elisio.desafioItau.framework.adapter.out;

import com.elisio.desafioItau.application.port.out.LivroDBPortOut;
import com.elisio.desafioItau.domain.Livro;
import com.elisio.desafioItau.framework.adapter.out.persistence.LivroRepository;
import com.elisio.desafioItau.framework.exceptions.LivroException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class LivrosDbPortOutImpl implements LivroDBPortOut {

    private final LivroRepository livroRepository;

    public LivrosDbPortOutImpl(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }


    @Override
    public List<Livro> getAllLivros() {
        return livroRepository.findAll();
    }

    @Override
    public Livro getById(Long id) {
        return livroRepository.findById(id).orElseThrow(() -> new LivroException("Book not founded " + id));
    }

    @Override
    public Livro saveLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    @Override
    public Livro updateLivro(Long id, Livro livro) {
        if (!livroRepository.existsById(id)) {
            throw new LivroException("Book not founded to update: " + id);
        }

        livro.setId(id);
        return livroRepository.save(livro);
    }

    @Override
    public void deleteLivro(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new LivroException("Book not founded to update: " + id);
        }

        livroRepository.deleteById(id);
    }
}
