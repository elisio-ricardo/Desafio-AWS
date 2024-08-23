package com.elisio.desafioItau.framework.adapter.out.persistence;

import com.elisio.desafioItau.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
