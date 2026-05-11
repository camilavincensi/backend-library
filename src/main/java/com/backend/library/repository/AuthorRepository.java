package com.backend.library.repository;

import com.backend.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorRepository
        extends JpaRepository<Author, Long> {

    List<Author> findByNameContainingIgnoreCase(
            String name
    );
}