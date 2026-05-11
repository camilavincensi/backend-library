package com.backend.library.mapper;

import com.backend.library.dto.BookDTO;
import com.backend.library.entity.Author;
import com.backend.library.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    // ✅ ENTITY → DTO
    public BookDTO toDTO(Book book) {

        BookDTO dto = new BookDTO();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setGenre(book.getGenre());
        dto.setStatus(book.getStatus().name());
        dto.setRating(book.getRating());
        dto.setComment(book.getComment());
        dto.setFavorite(book.getFavorite());
        dto.setCoverUrl(book.getCoverUrl());

        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
        }

        return dto;
    }

    // ✅ DTO → ENTITY
    public Book toEntity(BookDTO dto, Author author) {

        Book book = new Book();

        // ⚠️ só seta ID se existir (update)
        if (dto.getId() != null) {
            book.setId(dto.getId());
        }

        book.setTitle(dto.getTitle());
        book.setGenre(dto.getGenre());
        book.setStatus(Enum.valueOf(
                com.backend.library.entity.BookStatus.class,
                dto.getStatus()
        ));
        book.setRating(dto.getRating());
        book.setComment(dto.getComment());
        book.setFavorite(dto.getFavorite());
        book.setCoverUrl(dto.getCoverUrl());

        book.setAuthor(author);

        return book;
    }
}