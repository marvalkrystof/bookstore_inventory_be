/**
 * Global exception handler for the bookstore inventory system.
 * <p>
 * This class provides centralized exception handling for various application errors,
 * ensuring consistent error responses.
 * </p>
 *
 * <b>Handled Exceptions:</b>
 * <ul>
 *   <li>{@link DataNotFoundException} - Handles entity not found scenarios.</li>
 *   <li>{@link BadCredentialsException} - Handles incorrect authentication credentials.</li>
 *   <li>{@link PageOutOfBoundsException} - Handles invalid pagination requests.</li>
 *   <li>{@link MethodArgumentNotValidException} - Handles validation errors in request bodies.</li>
 *   <li>{@link HttpMessageNotReadableException} - Handles incorrect data types in request bodies.</li>
 *   <li>{@link DataIntegrityViolationException} - Handles foreign key constraint violations.</li>
 *   <li>{@link MissingIdentifierException} - Handles missing entity identifier errors.</li>
 * </ul>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.error.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.krystofmarval.bookstoreinventory.error.exception.DataNotFoundException;
import com.krystofmarval.bookstoreinventory.error.exception.MissingIdentifierException;
import com.krystofmarval.bookstoreinventory.error.exception.PageOutOfBoundsException;
import com.krystofmarval.bookstoreinventory.model.payload.ApiMessage;
import com.krystofmarval.bookstoreinventory.model.payload.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Handles exceptions when requested entity data is not found.
     *
     * @param exception The thrown exception.
     * @return ResponseEntity with NOT_FOUND status and error details.
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND);
        errorMessage.addErrorMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    /**
     * Handles authentication failures due to incorrect credentials.
     *
     * @param exception The thrown exception.
     * @return ResponseEntity with UNAUTHORIZED status and error message.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleInvalidCredentials(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED);
        errorMessage.addErrorMessage("Incorrect username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    /**
     * Handles invalid pagination requests when the requested page is out of bounds.
     *
     * @param exception The thrown exception.
     * @return ResponseEntity with BAD_REQUEST status and error details.
     */
    @ExceptionHandler(PageOutOfBoundsException.class)
    public ResponseEntity<ErrorMessage> handlePageOutOfBoundsException(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST);
        errorMessage.addErrorMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    /**
     * Handles validation errors in request bodies, such as missing or incorrect fields.
     *
     * @param ex The thrown exception.
     * @return ResponseEntity with BAD_REQUEST status and a list of validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiMessage> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> "Field: " + error.getField() + " - " + error.getDefaultMessage())
                .collect(Collectors.toList());
        ErrorMessage errorResponse = new ErrorMessage(HttpStatus.BAD_REQUEST);
        errorResponse.addErrorMessage(errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Handles cases where an invalid data type is provided in a request body.
     *
     * @param ex The thrown exception.
     * @return ResponseEntity with BAD_REQUEST status and details about the invalid data type.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorMessage errorResponse = new ErrorMessage(HttpStatus.BAD_REQUEST);

        if (ex.getCause() instanceof JsonMappingException) {
            JsonMappingException jsonMappingException = (JsonMappingException) ex.getCause();
            String fieldName = jsonMappingException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .findFirst()
                    .orElse("Unknown field");

            errorResponse.addErrorMessage("Invalid datatype for field: " + fieldName);
        } else {
            errorResponse.addErrorMessage("Please provide valid data types in the request body.");
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Handles database constraint violations, such as foreign key constraints.
     *
     * @param ex The thrown exception.
     * @return ResponseEntity with BAD_REQUEST status and details about the constraint violation.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiMessage> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorMessage errorResponse = new ErrorMessage(HttpStatus.BAD_REQUEST);
        String errorMessage = ex.getMostSpecificCause().getMessage();

        // Extract table, field, and value details from PostgreSQL error message
        Pattern pattern = Pattern.compile("Key \\((.*?)\\)=\\((.*?)\\) is not present in table \"(.*?)\"");
        Matcher matcher = pattern.matcher(errorMessage);

        if (matcher.find()) {
            String fieldName = matcher.group(1);
            String invalidValue = matcher.group(2);
            String tableName = matcher.group(3);

            // Potential security risk, leaking table names to the client, but it's fine for this project
            errorResponse.addErrorMessage(
                    "Foreign key constraint violation: Field '" + fieldName + "' with value '" + invalidValue +
                            "' does not exist in table '" + tableName + "'."
            );
        } else {
            errorResponse.addErrorMessage("Foreign key constraint violation occurred.");
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Handles cases where an entity is missing a required identifier.
     *
     * @param ex The thrown exception.
     * @return ResponseEntity with BAD_REQUEST status and error details.
     */
    @ExceptionHandler(MissingIdentifierException.class)
    public ResponseEntity<ApiMessage> handleMissingIdentifierException(MissingIdentifierException ex) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST);
        errorMessage.addErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    /**
     * Handles general IllegalArgumentExceptions thrown by the application.
     *
     * @param ex The thrown exception.
     * @return ResponseEntity with BAD_REQUEST status and error details.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST);
        errorMessage.addErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
