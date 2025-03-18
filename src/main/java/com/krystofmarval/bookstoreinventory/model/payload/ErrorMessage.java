/**
 * API response message for error handling.
 * <p>
 * This class extends {@link ApiMessage} to provide a standardized structure
 * for returning error messages in API responses.
 * </p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.model.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ErrorMessage extends ApiMessage {

    /**
     * Constructs an {@code ErrorMessage} with the given HTTP status.
     *
     * @param status The HTTP status of the error response.
     */
    public ErrorMessage(HttpStatus status) {
        super(status);
    }

    /**
     * Adds a single error message to the metadata.
     *
     * @param message The error message to be added.
     */
    public void addErrorMessage(String message) {
        addMetadata("error_reason", message);
    }

    /**
     * Adds a list of error messages to the metadata.
     *
     * @param errors A list of error messages to be included in the response.
     */
    public void addErrorMessage(List<String> errors) {
        addMetadata("errors", errors);
    }

    /**
     * Retrieves the HTTP status of the error response.
     *
     * @return The HTTP status associated with this error message.
     */
    public HttpStatus getStatus() {
        return status;
    }
}
