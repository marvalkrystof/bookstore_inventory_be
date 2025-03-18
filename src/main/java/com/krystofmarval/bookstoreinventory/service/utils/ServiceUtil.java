/**
 * Utility class for service-layer operations.
 * <p>
 * This class provides helper methods for service-layer operations.
 * </p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.service.utils;

import com.krystofmarval.bookstoreinventory.error.exception.PageOutOfBoundsException;
import org.springframework.data.domain.Page;

public class ServiceUtil {

    /**
     * Validates a paginated response to ensure the requested page is within bounds.
     *
     * @param page          The paginated data retrieved.
     * @param requestedPage The page number requested by the user.
     * @param <T>           The type of data contained in the paginated response.
     * @throws PageOutOfBoundsException If the requested page exceeds the available pages.
     */
    public static <T> void validatePage(Page<T> page, int requestedPage) throws PageOutOfBoundsException {
        if (page.getTotalPages() > 0 && requestedPage >= page.getTotalPages()) {
            throw new PageOutOfBoundsException("Requested page " + requestedPage +
                    " exceeds total pages (" + page.getTotalPages() + "). Indexing is 0-based.");
        }
    }
}
