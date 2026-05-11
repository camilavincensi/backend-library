package com.backend.library.controller;

import com.backend.library.dto.BookDTO;
import com.backend.library.entity.Author;
import com.backend.library.entity.Book;
import com.backend.library.mapper.BookMapper;
import com.backend.library.repository.AuthorRepository;
import com.backend.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;
    private final BookMapper mapper;
    private final AuthorRepository authorRepository;

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO dto) {

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Book book = mapper.toEntity(dto, author);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDTO(service.create(book, author.getId())));
    }

    // ✅ LIST
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "id,asc") String sort
    ) {

        return ResponseEntity.ok(
                service.findAll(title, sort)
                        .stream()
                        .map(mapper::toDTO)
                        .toList()
        );
    }

    // ✅ FIND BY ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDTO(service.findById(id)));
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(
            @PathVariable Long id,
            @RequestBody BookDTO dto
    ) {

        Author author = null;

        if (dto.getAuthorId() != null) {
            author = authorRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found"));
        }

        Book book = mapper.toEntity(dto, author);

        return ResponseEntity.ok(
                mapper.toDTO(service.update(id, book, dto.getAuthorId()))
        );
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}