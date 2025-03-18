/**
 * Service class for managing genres in the bookstore inventory system.
 * <p>
 * This class provides methods for creating, retrieving, updating, and deleting genres,
 * as well as handling pagination and validation.
 * </p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.service;

import com.krystofmarval.bookstoreinventory.error.exception.DataNotFoundException;
import com.krystofmarval.bookstoreinventory.error.exception.PageOutOfBoundsException;
import com.krystofmarval.bookstoreinventory.model.Genre;
import com.krystofmarval.bookstoreinventory.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.krystofmarval.bookstoreinventory.service.utils.ServiceUtil.validatePage;

@Service
public class GenreService {

    /**
     * Repository for accessing genre data.
     */
    @Autowired
    private GenreRepository genreRepository;

    /**
     * Creates and saves a new genre in the database.
     *
     * @param genre The {@link Genre} object to be created.
     * @return The saved {@link Genre} entity.
     */
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    /**
     * Retrieves a genre by its unique ID.
     *
     * @param id The ID of the genre.
     * @return An {@link Optional} containing the found {@link Genre}.
     * @throws DataNotFoundException If the genre with the given ID does not exist.
     */
    public Optional<Genre> getGenreById(Long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isEmpty()) {
            throw new DataNotFoundException(id, Genre.class);
        }
        return genre;
    }

    /**
     * Retrieves all genres with pagination support.
     *
     * @param pageable The pagination parameters.
     * @return A paginated {@link Page} of {@link Genre} entities.
     * @throws PageOutOfBoundsException If the requested page is out of bounds.
     */
    public Page<Genre> getAllGenres(Pageable pageable) throws PageOutOfBoundsException {
        Page<Genre> page = genreRepository.findAll(pageable);
        validatePage(page, pageable.getPageNumber());
        return page;
    }

    /**
     * Updates an existing genre in the database.
     *
     * @param genre The {@link Genre} object with updated details.
     * @return The updated {@link Genre} entity.
     * @throws DataNotFoundException If the genre does not exist in the database.
     */
    public Genre updateGenre(Genre genre) {
        if (!genreRepository.existsById(genre.getId())) {
            throw new DataNotFoundException(genre.getId(), Genre.class);
        }
        return genreRepository.save(genre);
    }

    /**
     * Deletes a genre from the database.
     *
     * @param id The ID of the genre to delete.
     * @throws DataNotFoundException If the genre does not exist.
     */
    public void deleteGenre(Long id) {
        if (this.getGenreById(id).isEmpty()) {
            throw new DataNotFoundException(id, Genre.class);
        }
        genreRepository.deleteById(id);
    }
}
