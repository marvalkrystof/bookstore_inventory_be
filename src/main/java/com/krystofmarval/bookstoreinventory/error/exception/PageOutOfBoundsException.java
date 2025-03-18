/**
 * Exception thrown when a requested page number is out of bounds.
 * <p>
 * This exception is used to indicate that the requested page index
 * exceeds the available number of pages in a paginated result.
 * </p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.error.exception;

public class PageOutOfBoundsException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message describing the out-of-bounds page error.
     */
    public PageOutOfBoundsException(String message) {
        super(message);
    }
}
