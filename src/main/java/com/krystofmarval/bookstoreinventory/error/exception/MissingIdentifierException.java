/**
 * Exception thrown when a required identifier is missing for a given entity.
 * <p>
 * This exception is used to indicate that an entity object of a specified class
 * does not contain a necessary identifier when expected.
 * </p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.error.exception;

import com.krystofmarval.bookstoreinventory.model.ModelBase;

public class MissingIdentifierException extends RuntimeException {

    /**
     * Constructs a new exception indicating that an identifier is missing
     * for a specified entity class.
     *
     * @param modelClass The class type of the entity that is missing an identifier.
     */
    public MissingIdentifierException(Class<? extends ModelBase> modelClass) {
        super("Missing identifier for provided " + modelClass.getSimpleName() + " object");
    }
}
