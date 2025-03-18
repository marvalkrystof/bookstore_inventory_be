/**
 * API response message for paginated data.
 * <p>
 * This class extends {@link DataApiMessage} to include pagination metadata,
 * ensuring a standardized structure for paginated API responses.
 * </p>
 *
 * @param <T> The type of data contained in the response.
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PaginatedDataApiMessage<T> extends DataApiMessage<T> {

    /**
     * The current page number in the pagination.
     */
    @JsonIgnore
    private int page;

    /**
     * The number of elements per page.
     */
    @JsonIgnore
    private int size;

    /**
     * The total number of elements available in the dataset.
     */
    @JsonIgnore
    private long totalElements;

    /**
     * The total number of pages available.
     */
    @JsonIgnore
    private int totalPages;

    /**
     * Constructs a {@code PaginatedDataApiMessage} with the given HTTP status and paginated data.
     *
     * @param status   The HTTP status of the response.
     * @param pageData The paginated data containing content and pagination details.
     */
    public PaginatedDataApiMessage(HttpStatus status, Page<T> pageData) {
        super(status, pageData.getContent());
        this.addMetadata("page", pageData.getNumber());
        this.addMetadata("size", pageData.getSize());
        this.addMetadata("total_elements", pageData.getTotalElements());
        this.addMetadata("total_pages", pageData.getTotalPages());
    }
}
