package com.backend.library.controller;

import com.backend.library.dto.AuthorDTO;
import com.backend.library.entity.Author;
import com.backend.library.mapper.AuthorMapper;
import com.backend.library.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;
    private final AuthorMapper mapper;

    @PostMapping
    public ResponseEntity<AuthorDTO> create(@Valid @RequestBody AuthorDTO dto) {

        Author author = mapper.toEntity(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDTO(service.create(author)));
    }

    @GetMapping
    public List<Author> findAll(
            @RequestParam(required = false) String name
    ) {

        return service.findAll(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable Long id) {

        return ResponseEntity.ok(
                mapper.toDTO(service.findById(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AuthorDTO dto
    ) {

        Author author = mapper.toEntity(dto);

        return ResponseEntity.ok(
                mapper.toDTO(service.update(id, author))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}