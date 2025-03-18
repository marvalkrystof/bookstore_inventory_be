/**
 * Service class for managing books in the bookstore inventory system.
 * <p>
 * This class provides methods for creating, retrieving, updating, deleting, and searching books.
 * It ensures that books are associated with valid authors and genres before saving them.
 * </p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.service;

import com.krystofmarval.bookstoreinventory.error.exception.DataNotFoundException;
import com.krystofmarval.bookstoreinventory.error.exception.MissingIdentifierException;
import com.krystofmarval.bookstoreinventory.error.exception.PageOutOfBoundsException;
import com.krystofmarval.bookstoreinventory.model.Author;
import com.krystofmarval.bookstoreinventory.model.Book;
import com.krystofmarval.bookstoreinventory.model.Genre;
import com.krystofmarval.bookstoreinventory.repository.BookRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.krystofmarval.bookstoreinventory.service.utils.ServiceUtil.validatePage;

@Service
public class BookService {

    /**
     * Repository for accessing book data.
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * Service for managing authors.
     */
    @Autowired
    private AuthorService authorService;

    /**
     * Service for managing genres.
     */
    @Autowired
    private GenreService genreService;

    /**
     * Saves a book after validating its author and genre.
     *
     * @param book The {@link Book} object to be saved.
     * @return The saved {@link Book} entity.
     * @throws DataNotFoundException       If the author or genre does not exist.
     * @throws MissingIdentifierException If the author or genre has no ID.
     */
    public Book saveBook(Book book) throws DataNotFoundException, MissingIdentifierException {
        if (book.getAuthor() == null || book.getAuthor().getId() == null) {
            throw new MissingIdentifierException(Author.class);
        }
        if (book.getGenre() == null || book.getGenre().getId() == null) {
            throw new MissingIdentifierException(Genre.class);
        }
        Optional<Author> author = authorService.getAuthorById(book.getAuthor().getId());
        if (author.isEmpty()) {
            throw new DataNotFoundException(book.getAuthor().getId(), Author.class);
        }
        Optional<Genre> genre = genreService.getGenreById(book.getGenre().getId());
        if (genre.isEmpty()) {
            throw new DataNotFoundException(book.getGenre().getId(), Genre.class);
        }
        return bookRepository.save(book);
    }

    /**
     * Creates a new book entry in the database.
     *
     * @param book The {@link Book} object to be created.
     * @return The saved {@link Book} entity.
     * @throws DataNotFoundException       If the author or genre does not exist.
     * @throws MissingIdentifierException If the author or genre has no ID.
     */
    public Book createBook(Book book) throws DataNotFoundException, MissingIdentifierException {
        return this.saveBook(book);
    }

    /**
     * Retrieves a book by its unique ID.
     *
     * @param id The ID of the book.
     * @return An {@link Optional} containing the found {@link Book}.
     * @throws DataNotFoundException If the book with the given ID does not exist.
     */
    public Optional<Book> getBookById(Long id) throws DataNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new DataNotFoundException(id, Book.class);
        }
        return book;
    }

    /**
     * Retrieves all books with pagination support.
     *
     * @param pageable The pagination parameters.
     * @return A paginated {@link Page} of {@link Book} entities.
     * @throws PageOutOfBoundsException If the requested page is out of bounds.
     */
    public Page<Book> getAllBooks(Pageable pageable) throws PageOutOfBoundsException {
        Page<Book> page = bookRepository.findAll(pageable);
        validatePage(page, pageable.getPageNumber());
        return page;
    }

    /**
     * Updates an existing book in the database.
     *
     * @param book The {@link Book} object with updated details.
     * @return The updated {@link Book} entity.
     * @throws DataNotFoundException       If the book does not exist.
     * @throws MissingIdentifierException If the author or genre has no ID.
     */
    public Book updateBook(Book book) throws DataNotFoundException, MissingIdentifierException {
        if (!bookRepository.existsById(book.getId())) {
            throw new DataNotFoundException(book.getId(), Book.class);
        }
        return this.saveBook(book);
    }

    /**
     * Deletes a book from the database.
     *
     * @param id The ID of the book to delete.
     * @throws DataNotFoundException If the book does not exist.
     */
    public void deleteBook(Long id) throws DataNotFoundException {
        if (this.getBookById(id).isEmpty()) {
            throw new DataNotFoundException(id, Book.class);
        }
        bookRepository.deleteById(id);
    }

    /**
     * Searches books based on title, author name, or genre name.
     *
     * @param title  The title of the book (optional).
     * @param author The author's name (optional).
     * @param genre  The genre name (optional).
     * @param pageable The pagination parameters.
     * @return A paginated {@link Page} of books that match the search criteria.
     */
    public Page<Book> searchBooks(String title, String author, String genre, Pageable pageable) {
        return bookRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                ));
            }

            if (author != null && !author.isEmpty()) {
                Join<Book, Author> authorJoin = root.join("author", JoinType.LEFT);
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(authorJoin.get("name")),
                        "%" + author.toLowerCase() + "%"
                ));
            }

            if (genre != null && !genre.isEmpty()) {
                Join<Book, Genre> genreJoin = root.join("genre", JoinType.LEFT);
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(genreJoin.get("name")),
                        "%" + genre.toLowerCase() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }
}
