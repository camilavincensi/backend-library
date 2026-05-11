package com.backend.library.service;

import com.backend.library.entity.Author;
import com.backend.library.exception.NotFoundException;
import com.backend.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository repository;

    public Author create(Author author) {

        validateAuthor(author);

        try {
            log.info("Creating author: {}", author.getName());

            Author saved = repository.save(author);

            log.info("Author created successfully. ID={}", saved.getId());

            return saved;

        } catch (DataAccessException e) {
            log.error("Database error while creating author: {}", author.getName(), e);
            throw new RuntimeException("Error creating author");
        }
    }

    public List<Author> findAll(String name) {

        try {
            log.info("Searching authors with filter: {}", name);

            List<Author> authors = (name != null && !name.isBlank())
                    ? repository.findByNameContainingIgnoreCase(name)
                    : repository.findAll();

            log.info("Found {} authors", authors.size());

            return authors;

        } catch (DataAccessException e) {
            log.error("Database error while fetching authors", e);
            throw new RuntimeException("Error fetching authors");
        }
    }

    public Author findById(Long id) {

        log.info("Searching author by ID={}", id);

        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Author not found. ID={}", id);
                    return new NotFoundException("Author not found");
                });
    }

    public Author update(Long id, Author updated) {

        validateAuthor(updated);

        try {
            log.info("Updating author ID={}", id);

            Author author = findById(id);

            author.setName(updated.getName());

            Author saved = repository.save(author);

            log.info("Author updated successfully. ID={}", id);

            return saved;

        } catch (DataAccessException e) {
            log.error("Database error while updating author ID={}", id, e);
            throw new RuntimeException("Error updating author");
        }
    }

    public void delete(Long id) {

        try {
            log.info("Deleting author ID={}", id);

            Author author = findById(id);

            if (author.getBooks() != null && !author.getBooks().isEmpty()) {
                log.warn("Delete blocked. Author ID={} has {} books",
                        id, author.getBooks().size());

                throw new IllegalStateException(
                        "Cannot delete author because it has associated books"
                );
            }

            repository.delete(author);

            log.info("Author deleted successfully. ID={}", id);

        } catch (DataAccessException e) {
            log.error("Database error while deleting author ID={}", id, e);
            throw new RuntimeException("Error deleting author");
        }
    }

    private void validateAuthor(Author author) {

        if (author == null) {
            log.error("Author cannot be null");
            throw new IllegalArgumentException("Author cannot be null");
        }

        if (author.getName() == null || author.getName().isBlank()) {
            log.error("Author name cannot be empty");
            throw new IllegalArgumentException("Author name cannot be empty");
        }
    }
}