/**
 * Base class for API response messages.
 * <p>
 * This class provides a standardized structure for API responses,
 * including an HTTP status and additional metadata for enhanced readability.
 * </p>
 *
 * <b>Key Features:</b>
 * <ul>
 *   <li>Encapsulates metadata for API responses.</li>
 *   <li>Provides utility methods for adding messages and metadata.</li>
 *   <li>Ensures immutability of metadata to prevent unintended modifications.</li>
 * </ul>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.model.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.*;

@Data
@NoArgsConstructor
public class ApiMessage {

    /**
     * The HTTP status associated with the response.
     */
    protected HttpStatus status;

    /**
     * Metadata map containing additional information related to the response.
     */
    @Setter(AccessLevel.NONE)
    private Map<String, Object> metadata;

    /**
     * Constructs an {@code ApiMessage} with the given HTTP status.
     *
     * @param status The HTTP status of the response.
     */
    public ApiMessage(HttpStatus status) {
        this.status = status;
        // Using LinkedHashMap to maintain the order of metadata for better readability
        this.metadata = new LinkedHashMap<>();
    }

    /**
     * Retrieves an unmodifiable view of the metadata map.
     * <p>
     * This ensures that metadata can only be modified through designated methods.
     * </p>
     *
     * @return Unmodifiable map containing metadata entries.
     */
    public Map<String, Object> getMetadata() {
        return Collections.unmodifiableMap(metadata);
    }

    /**
     * Adds a message to the metadata.
     *
     * @param message The message to be added.
     */
    public void addMessage(String message) {
        metadata.put("message", message);
    }

    /**
     * Adds a key-value pair of metadata with a {@code String} value.
     *
     * @param key   The metadata key.
     * @param value The metadata value.
     */
    public void addMetadata(String key, String value) {
        metadata.put(key, value);
    }

    /**
     * Adds a key-value pair of metadata with an {@code int} value.
     *
     * @param key   The metadata key.
     * @param value The metadata value.
     */
    public void addMetadata(String key, int value) {
        metadata.put(key, value);
    }

    /**
     * Adds a key-value pair of metadata with a {@code double} value.
     *
     * @param key   The metadata key.
     * @param value The metadata value.
     */
    public void addMetadata(String key, double value) {
        metadata.put(key, value);
    }

    /**
     * Adds a key-value pair of metadata with a {@code long} value.
     *
     * @param key   The metadata key.
     * @param value The metadata value.
     */
    public void addMetadata(String key, long value) {
        metadata.put(key, value);
    }

    /**
     * Adds a key-value pair of metadata with a {@code boolean} value.
     *
     * @param key   The metadata key.
     * @param value The metadata value.
     */
    public void addMetadata(String key, boolean value) {
        metadata.put(key, value);
    }

    /**
     * Adds a key-value pair of metadata with a {@code List<String>} value.
     *
     * @param key   The metadata key.
     * @param value The metadata value.
     */
    public void addMetadata(String key, List<String> value) {
        metadata.put(key, value);
    }
}
