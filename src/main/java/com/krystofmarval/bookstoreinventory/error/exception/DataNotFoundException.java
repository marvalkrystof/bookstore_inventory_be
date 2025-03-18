/**
 * Exception thrown when requested data is not found in the database.
 * <p>
 * This exception is used to indicate that an entity of a specified class
 * with a given ID does not exist in the database.
 * </p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.error.exception;

import com.krystofmarval.bookstoreinventory.model.ModelBase;

public class DataNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message describing the error.
     */
    public DataNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with a detailed message including the entity class and ID.
     *
     * @param id         The ID of the entity that was not found.
     * @param modelClass The class type of the entity that was searched for.
     */
    public DataNotFoundException(Long id, Class<? extends ModelBase> modelClass) {
        super("Data of class " + modelClass.getSimpleName() + " with id " + id + " not found in the database");
    }
}
