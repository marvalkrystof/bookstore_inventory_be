package com.krystofmarval.bookstoreinventory.controller;

import com.krystofmarval.bookstoreinventory.error.exception.DataNotFoundException;
import com.krystofmarval.bookstoreinventory.error.exception.MissingIdentifierException;
import com.krystofmarval.bookstoreinventory.error.exception.PageOutOfBoundsException;
import com.krystofmarval.bookstoreinventory.model.Book;
import com.krystofmarval.bookstoreinventory.model.payload.ApiMessage;
import com.krystofmarval.bookstoreinventory.model.payload.DataApiMessage;
import com.krystofmarval.bookstoreinventory.model.payload.PaginatedDataApiMessage;
import com.krystofmarval.bookstoreinventory.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * REST controller responsible for CRUD operations related to Books.
 * <p>
 * Provides endpoints to create, read, update, delete, and search books in the bookstore inventory system.
 * Supports pagination when retrieving multiple books.
 * </p>
 * <b>Endpoints:</b>
 * <ul>
 *   <li>POST /books - Creates a new book (Admin only).</li>
 *   <li>GET /books/{id} - Retrieves a book by ID.</li>
 *   <li>GET /books - Retrieves all books with pagination.</li>
 *   <li>PUT /books/{id} - Updates an existing book (Admin only).</li>
 *   <li>DELETE /books/{id} - Deletes a book by ID (Admin only).</li>
 * </ul>
 *
 * <b>Authorization:</b>
 * <p>Some endpoints are restricted to admin users only.</p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/books", produces = "application/json")
public class BookController {

    /**
     * Service responsible for book-related operations.
     */
    @Autowired
    private BookService bookService;

    /**
     * Creates a new book entry in the inventory.
     *
     * @param book Book object to be created.
     * @return ResponseEntity containing the created book and a success message.
     * @throws DataNotFoundException if the author or genre is not found.
     * @throws MissingIdentifierException if the author id or genre id is missing.
     */
    @PostMapping
    public ResponseEntity<ApiMessage> createBook(@RequestBody @Valid Book book) throws DataNotFoundException, MissingIdentifierException {
        Book created = bookService.createBook(book);
        ApiMessage response = new DataApiMessage<Book>(HttpStatus.OK, Collections.singletonList(created));
        response.addMessage("Book with id " + created.getId() + " created successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a specific book by its ID.
     *
     * @param id Unique identifier of the book.
     * @return ResponseEntity containing the book details.
     * @throws DataNotFoundException if the book is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiMessage> getBook(@PathVariable Long id) throws DataNotFoundException {
        Book book = bookService.getBookById(id).orElseThrow(() -> new DataNotFoundException("Book not found"));
        ApiMessage response = new DataApiMessage<>(HttpStatus.OK, Collections.singletonList(book));
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all books with pagination.
     *
     * @param page Page number to retrieve (default is 0).
     * @param size Number of items per page (default is 10).
     * @return ResponseEntity containing paginated books.
     */
    @GetMapping
    public ResponseEntity<ApiMessage> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookService.getAllBooks(pageable);
        ApiMessage response = new PaginatedDataApiMessage<>(HttpStatus.OK, booksPage);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates details of an existing book.
     *
     * @param id Unique identifier of the book to update.
     * @param book Book object with updated details.
     * @return ResponseEntity containing updated book details and confirmation message.
     * @throws DataNotFoundException if the book with the given ID does not exist.
     * @throws MissingIdentifierException if the author id or genre id is missing.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiMessage> updateBook(@PathVariable Long id, @RequestBody @Valid Book book) throws DataNotFoundException, MissingIdentifierException {
        book.setId(id);
        Book updated = bookService.updateBook(book);
        ApiMessage response = new DataApiMessage<>(HttpStatus.OK, Collections.singletonList(updated));
        response.addMessage("Book with id " + updated.getId() + " updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a book from the inventory by ID.
     *
     * @param id Unique identifier of the book to delete.
     * @return ResponseEntity containing confirmation message.
     * @throws DataNotFoundException if the book with the given ID does not exist.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiMessage> deleteBook(@PathVariable Long id) throws DataNotFoundException {
        bookService.deleteBook(id);
        ApiMessage response = new ApiMessage(HttpStatus.OK);
        response.addMessage("Book with id " + id + " deleted successfully");
        return ResponseEntity.ok(response);
    }


    /**
     * Searches for books based on title, author, and/or genre.
     *
     * @param title  Optional book title to search for.
     * @param author Optional author name to search for.
     * @param genre  Optional genre name to search for.
     * @param page   Page number for pagination (default is 0).
     * @param size   Page size for pagination (default is 10).
     * @return A ResponseEntity containing a paginated list of books.
     */
    @GetMapping("/search")
    public ResponseEntity<ApiMessage> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookService.searchBooks(title, author, genre, pageable);
        ApiMessage response = new PaginatedDataApiMessage<>(HttpStatus.OK, booksPage);
        response.addMetadata("search_title", title);
        response.addMetadata("search_author", author);
        response.addMetadata("search_genre", genre);
        response.addMessage("Search executed successfully");
        return ResponseEntity.ok(response);
    }
}
