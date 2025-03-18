/**
 * Service class for managing authors in the bookstore inventory system.
 * <p>
 * This class provides methods for creating, retrieving, updating, and deleting authors,
 * as well as handling pagination and validation.
 * </p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.service;

import com.krystofmarval.bookstoreinventory.error.exception.DataNotFoundException;
import com.krystofmarval.bookstoreinventory.error.exception.PageOutOfBoundsException;
import com.krystofmarval.bookstoreinventory.model.Author;
import com.krystofmarval.bookstoreinventory.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.krystofmarval.bookstoreinventory.service.utils.ServiceUtil.validatePage;

@Service
public class AuthorService {

    /**
     * Repository for accessing author data.
     */
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Creates and saves a new author in the database.
     *
     * @param author The {@link Author} object to be created.
     * @return The saved {@link Author} entity.
     */
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    /**
     * Retrieves an author by their unique ID.
     *
     * @param id The ID of the author.
     * @return An {@link Optional} containing the found {@link Author}.
     * @throws DataNotFoundException If the author with the given ID does not exist.
     */
    public Optional<Author> getAuthorById(Long id) throws DataNotFoundException {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            throw new DataNotFoundException(id, Author.class);
        }
        return author;
    }

    /**
     * Retrieves all authors with pagination support.
     *
     * @param pageable The pagination parameters.
     * @return A paginated {@link Page} of {@link Author} entities.
     * @throws PageOutOfBoundsException If the requested page is out of bounds.
     */
    public Page<Author> getAllAuthors(Pageable pageable) throws PageOutOfBoundsException {
        Page<Author> page = authorRepository.findAll(pageable);
        validatePage(page, pageable.getPageNumber());
        return page;
    }

    /**
     * Updates an existing author in the database.
     *
     * @param author The {@link Author} object with updated details.
     * @return The updated {@link Author} entity.
     * @throws DataNotFoundException If the author does not exist in the database.
     */
    public Author updateAuthor(Author author) throws DataNotFoundException {
        if (!authorRepository.existsById(author.getId())) {
            throw new DataNotFoundException(author.getId(), Author.class);
        }
        return authorRepository.save(author);
    }

    /**
     * Deletes an author from the database.
     *
     * @param id The ID of the author to delete.
     * @throws DataNotFoundException If the author does not exist.
     */
    public void deleteAuthor(Long id) throws DataNotFoundException {
        if (this.getAuthorById(id).isEmpty()) {
            throw new DataNotFoundException(id, Author.class);
        }
        authorRepository.deleteById(id);
    }
}
