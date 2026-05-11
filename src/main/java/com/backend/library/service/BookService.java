package com.backend.library.service;

import com.backend.library.entity.Author;
import com.backend.library.entity.Book;
import com.backend.library.exception.NotFoundException;
import com.backend.library.repository.AuthorRepository;
import com.backend.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    // ✅ CREATE com autor
    @Transactional
    public Book create(Book book, Long authorId) {

        log.info("Creating book: {}", book.getTitle());

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author not found"));

        book.setAuthor(author);

        return bookRepository.save(book);
    }

    // ✅ LIST com filtro + ordenação segura
    public List<Book> findAll(String title, String sort) {

        log.info("Searching books with filter: {}", title);

        Sort sorting = Sort.by("title").ascending();

        if (sort != null && sort.contains(",")) {
            String[] sortParams = sort.split(",");
            sorting = Sort.by(
                    Sort.Direction.fromString(sortParams[1]),
                    sortParams[0]
            );
        }

        if (title != null && !title.isEmpty()) {
            return bookRepository.findByTitleContainingIgnoreCase(title, sorting);
        }

        return bookRepository.findAll(sorting);
    }

    // ✅ FIND BY ID
    public Book findById(Long id) {

        log.info("Searching book {}", id);

        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Book not found {}", id);
                    return new NotFoundException("Book not found");
                });
    }

    // ✅ UPDATE (inclusive autor)
    @Transactional
    public Book update(Long id, Book updated, Long authorId) {

        Book book = findById(id);

        book.setTitle(updated.getTitle());
        book.setGenre(updated.getGenre());
        book.setStatus(updated.getStatus());
        book.setRating(updated.getRating());
        book.setComment(updated.getComment());
        book.setStartDate(updated.getStartDate());
        book.setEndDate(updated.getEndDate());
        book.setFavorite(updated.getFavorite());
        book.setCoverUrl(updated.getCoverUrl());

        if (authorId != null) {
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new NotFoundException("Author not found"));
            book.setAuthor(author);
        }

        return bookRepository.save(book);
    }

    // ✅ DELETE
    public void delete(Long id) {

        Book book = findById(id);
        bookRepository.delete(book);

        log.info("Deleted book {}", id);
    }
}