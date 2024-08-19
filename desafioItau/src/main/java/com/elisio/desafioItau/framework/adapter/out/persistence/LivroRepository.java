package com.elisio.desafioItau.framework.adapter.out.persistence;

import com.elisio.desafioItau.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LivroRepository extends JpaRepository<Book, Long> {
}
