package com.backend.library.service;

import com.backend.library.entity.Author;
import com.backend.library.entity.Book;
import com.backend.library.exception.NotFoundException;
import com.backend.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldCreateBook() {
        Book book = new Book();
        Author author = new Author();
        book.setTitle("Teste");
        author.setId(1L);

        when(bookRepository.save(any())).thenReturn(book);

        Book result = bookService.create(book, author.getId());

        assertEquals("Teste", result.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            bookService.findById(1L);
        });
    }
}