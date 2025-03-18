/**
 * API response message containing data.
 * <p>
 * This class extends {@link ApiMessage} to include a list of data items
 * in addition to the standard response structure.
 * </p>
 *
 * @param <T> The type of data contained in the response.
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.model.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DataApiMessage<T> extends ApiMessage {

    /**
     * The list of data items returned in the API response.
     */
    protected List<T> data;

    /**
     * Constructs a {@code DataApiMessage} with the given HTTP status and data list.
     *
     * @param status The HTTP status of the response.
     * @param data   The list of data items included in the response.
     */
    public DataApiMessage(HttpStatus status, List<T> data) {
        super(status);
        this.data = data;
    }
}
