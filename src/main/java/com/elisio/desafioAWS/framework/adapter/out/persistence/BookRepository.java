package com.elisio.desafioAWS.framework.adapter.out.persistence;

import com.elisio.desafioAWS.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
