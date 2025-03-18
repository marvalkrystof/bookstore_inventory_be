package com.krystofmarval.bookstoreinventory.controller;

import com.krystofmarval.bookstoreinventory.error.exception.DataNotFoundException;
import com.krystofmarval.bookstoreinventory.error.exception.PageOutOfBoundsException;
import com.krystofmarval.bookstoreinventory.model.Author;
import com.krystofmarval.bookstoreinventory.model.payload.ApiMessage;
import com.krystofmarval.bookstoreinventory.model.payload.DataApiMessage;
import com.krystofmarval.bookstoreinventory.model.payload.PaginatedDataApiMessage;
import com.krystofmarval.bookstoreinventory.service.AuthorService;
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
import java.util.Optional;

/**
 * REST controller responsible for CRUD operations related to Authors.
 * <p>
 * Provides endpoints to create, read, update, and delete authors from the bookstore inventory system.
 * Supports pagination when retrieving multiple authors.
 * </p>
 * <b>Endpoints:</b>
 * <ul>
 *   <li>POST /api/authors - Create a new author (Admin only).</li>
 *   <li>GET /api/authors/{id} - Retrieve author by ID.</li>
 *   <li>GET /api/authors - Retrieve all authors with pagination support.</li>
 *   <li>PUT /api/authors/{id} - Update an existing author (Admin only).</li>
 *   <li>DELETE /api/authors/{id} - Delete an author by ID (Admin only).</li>
 * </ul>
 *
 * <b>Authorization:</b>
 * <p>Some endpoints are restricted to users with the ADMIN role.</p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/authors", produces = "application/json")
public class AuthorController {

    /**
     * Service handling business logic for author management.
     */
    @Autowired
    private AuthorService authorService;

    /**
     * Creates a new author.
     *
     * @param author Author object to be created.
     * @return ResponseEntity containing the created author.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiMessage> createAuthor(@RequestBody @Valid Author author) {
        Author created = authorService.createAuthor(author);

        ApiMessage response = new DataApiMessage<>(HttpStatus.OK, Collections.singletonList(created));
        response.addMessage("Author with id " + created.getId() + " created successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves an author by their ID.
     *
     * @param id Author's unique identifier.
     * @return ResponseEntity containing the author details.
     * @throws DataNotFoundException if the author is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiMessage> getAuthorById(@PathVariable Long id) throws DataNotFoundException {
        Optional<Author> author = authorService.getAuthorById(id);

        ApiMessage response = new DataApiMessage<>(HttpStatus.OK, Collections.singletonList(author.get()));

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all authors with pagination.
     *
     * @param page Page number to retrieve (default is 0).
     * @param size Number of items per page (default is 10).
     * @return Paginated list of authors.
     * @throws PageOutOfBoundsException if the requested page is out of bounds.
     */
    @GetMapping
    public ResponseEntity<ApiMessage> getAllAuthors (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws PageOutOfBoundsException {

        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authorsPage = authorService.getAllAuthors(pageable);

        ApiMessage response = new PaginatedDataApiMessage<>(HttpStatus.OK, authorsPage);

        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing author's details.
     *
     * @param id ID of the author to update.
     * @param author Updated author details.
     * @return ResponseEntity with updated author information.
     * @throws DataNotFoundException if the author to update does not exist.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiMessage> updateAuthor(@PathVariable Long id, @Valid @RequestBody Author author) throws DataNotFoundException {
        author.setId(id);
        Author updated = authorService.updateAuthor(author);

        ApiMessage response = new DataApiMessage<>(HttpStatus.OK, Collections.singletonList(updated));
        response.addMessage("Author with id " + id + " updated successfully");

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes an author from the inventory.
     *
     * @param id ID of the author to delete.
     * @return ResponseEntity confirming the deletion.
     * @throws DataNotFoundException if the author to delete does not exist.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiMessage> deleteAuthor(@PathVariable Long id) throws DataNotFoundException {
        authorService.deleteAuthor(id);

        ApiMessage response = new ApiMessage(HttpStatus.OK);
        response.addMessage("Author with id " + id + " deleted successfully");

        return ResponseEntity.ok(response);
    }
}
