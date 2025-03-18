package com.krystofmarval.bookstoreinventory.service;

import com.krystofmarval.bookstoreinventory.error.exception.DataNotFoundException;
import com.krystofmarval.bookstoreinventory.model.Author;
import com.krystofmarval.bookstoreinventory.model.Book;
import com.krystofmarval.bookstoreinventory.model.Genre;
import com.krystofmarval.bookstoreinventory.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @Mock
    private GenreService genreService;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Author author;
    private Genre genre;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("J.K. Rowling");

        genre = new Genre();
        genre.setId(1L);
        genre.setName("Fantasy");

        book = new Book();
        book.setId(1L);
        book.setTitle("Harry Potter");
        book.setAuthor(author);
        book.setGenre(genre);
    }

    @Test
    void shouldCreateBookSuccessfully() {
        when(authorService.getAuthorById(author.getId())).thenReturn(Optional.of(author));
        when(genreService.getGenreById(genre.getId())).thenReturn(Optional.of(genre));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = bookService.createBook(book);

        assertNotNull(createdBook);
        assertEquals("Harry Potter", createdBook.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldThrowExceptionWhenAuthorNotFound() {
        when(authorService.getAuthorById(author.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(DataNotFoundException.class, () -> bookService.createBook(book));

        assertEquals("Data of class Author with id 1 not found in the database", exception.getMessage());
    }
}
