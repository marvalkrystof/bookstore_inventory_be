/**
 * Utility class for sending standardized error responses.
 * <p>
 * This class provides a method to send structured JSON error messages
 * in HTTP responses, ensuring consistency across the application.
 * </p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.error.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krystofmarval.bookstoreinventory.model.payload.ErrorMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ErrorResponseUtil {

    /**
     * ObjectMapper instance for serializing error messages into JSON format.
     */
    private final ObjectMapper objectMapper;

    /**
     * Constructs an instance of {@code ErrorResponseUtil} with a provided {@code ObjectMapper}.
     *
     * @param objectMapper The ObjectMapper used for JSON serialization.
     */
    public ErrorResponseUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Sends an error response with a given {@link ErrorMessage} as JSON.
     *
     * @param response     The {@link HttpServletResponse} to send the error message to.
     * @param errorMessage The {@link ErrorMessage} containing status and error details.
     * @throws IOException If an I/O error occurs while writing the response.
     */
    public void sendErrorResponse(HttpServletResponse response, ErrorMessage errorMessage) throws IOException {
        response.setStatus(errorMessage.getStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(errorMessage));
    }
}
