package com.krystofmarval.bookstoreinventory.controller;

import com.krystofmarval.bookstoreinventory.error.exception.DataNotFoundException;
import com.krystofmarval.bookstoreinventory.error.exception.PageOutOfBoundsException;
import com.krystofmarval.bookstoreinventory.model.Genre;
import com.krystofmarval.bookstoreinventory.model.payload.ApiMessage;
import com.krystofmarval.bookstoreinventory.model.payload.DataApiMessage;
import com.krystofmarval.bookstoreinventory.model.payload.PaginatedDataApiMessage;
import com.krystofmarval.bookstoreinventory.service.GenreService;
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
 * REST controller responsible for CRUD operations related to Genres.
 * <p>
 * Provides endpoints to create, read, update, and delete genres in the bookstore inventory system.
 * Supports pagination when retrieving multiple genres.
 * </p>
 * <b>Endpoints:</b>
 * <ul>
 *   <li>POST /genres - Creates a new genre (Admin only).</li>
 *   <li>GET /genres/{id} - Retrieves a genre by ID.</li>
 *   <li>GET /genres - Retrieves all genres with pagination.</li>
 *   <li>PUT /genres/{id} - Updates an existing genre (Admin only).</li>
 *   <li>DELETE /genres/{id} - Deletes a genre by ID (Admin only).</li>
 * </ul>
 *
 * <b>Authorization:</b>
 * <p>Some endpoints are restricted to admin users only.</p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/genres", produces = "application/json")
public class GenreController {

    /**
     * Service responsible for genre-related operations.
     */
    @Autowired
    private GenreService genreService;

    /**
     * Creates a new genre entry in the inventory.
     *
     * @param genre Genre object to be created.
     * @return ResponseEntity containing the created genre and a success message.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiMessage> createGenre(@RequestBody Genre genre) {
        Genre created = genreService.createGenre(genre);
        ApiMessage response = new DataApiMessage<>(HttpStatus.OK, Collections.singletonList(created));
        response.addMessage("Genre with id " + created.getId() + " created successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a specific genre by its ID.
     *
     * @param id Unique identifier of the genre.
     * @return ResponseEntity containing the genre details.
     * @throws DataNotFoundException if the genre is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiMessage> getGenreById(@PathVariable Long id) throws DataNotFoundException {
        Genre genre = genreService.getGenreById(id).orElseThrow(() -> new DataNotFoundException("Genre not found"));
        ApiMessage response = new DataApiMessage<>(HttpStatus.OK, Collections.singletonList(genre));
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all genres with pagination.
     *
     * @param page Page number to retrieve (default is 0).
     * @param size Number of items per page (default is 10).
     * @return ResponseEntity containing paginated genres.
     */
    @GetMapping
    public ResponseEntity<ApiMessage> getAllGenres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Genre> genresPage = genreService.getAllGenres(pageable);
        ApiMessage response = new PaginatedDataApiMessage<>(HttpStatus.OK, genresPage);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates details of an existing genre.
     *
     * @param id Unique identifier of the genre to update.
     * @param genre Genre object with updated details.
     * @return ResponseEntity containing updated genre details and confirmation message.
     * @throws DataNotFoundException if the genre with the given ID does not exist.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiMessage> updateGenre(@PathVariable Long id, @RequestBody Genre genre) throws DataNotFoundException {
        genre.setId(id);
        Genre updated = genreService.updateGenre(genre);
        ApiMessage response = new DataApiMessage<>(HttpStatus.OK, Collections.singletonList(updated));
        response.addMessage("Genre with id " + updated.getId() + " updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a genre from the inventory by ID.
     *
     * @param id Unique identifier of the genre to delete.
     * @return ResponseEntity containing confirmation message.
     * @throws DataNotFoundException if the genre with the given ID does not exist.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiMessage> deleteGenre(@PathVariable Long id) throws DataNotFoundException {
        genreService.deleteGenre(id);
        ApiMessage response = new ApiMessage(HttpStatus.OK);
        response.addMessage("Genre with id " + id + " deleted successfully");
        return ResponseEntity.ok(response);
    }
}
