/**
 * Custom authentication entry point for handling unauthorized access.
 * <p>
 * This class is triggered when an unauthenticated request is made to a secured resource.
 * It sends a structured JSON error response instead of the default Spring Security response.
 * </p>
 *
 * <b>Why this approach?</b>
 * <p>
 * Unlike global exception handling, this mechanism intercepts authentication failures
 * before they reach the controller logic, ensuring a consistent response format.
 * </p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.security.entrypoint;

import com.krystofmarval.bookstoreinventory.error.util.ErrorResponseUtil;
import com.krystofmarval.bookstoreinventory.model.payload.ErrorMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Utility class for sending structured error responses.
     */
    private final ErrorResponseUtil errorResponseUtil;

    /**
     * Handles unauthorized access attempts by returning a structured JSON error response.
     *
     * @param request       The HTTP request that triggered the unauthorized access.
     * @param response      The HTTP response where the error message is written.
     * @param authException The exception thrown when authentication fails.
     * @throws IOException      If an input or output error occurs while handling the response.
     * @throws ServletException If a servlet-specific error occurs.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED);
        errorMessage.addErrorMessage("Not authenticated");
        errorResponseUtil.sendErrorResponse(response, errorMessage);
    }
}
