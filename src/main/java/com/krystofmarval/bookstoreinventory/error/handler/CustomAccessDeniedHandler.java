/**
 * Custom handler for handling access denied exceptions in Spring Security.
 * <p>
 * This handler intercepts requests where the user does not have sufficient
 * permissions to access a resource and returns a standardized error response.
 * </p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.error.handler;

import com.krystofmarval.bookstoreinventory.error.util.ErrorResponseUtil;
import com.krystofmarval.bookstoreinventory.model.payload.ErrorMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Utility for sending standardized error responses.
     */
    private final ErrorResponseUtil errorResponseUtil;

    /**
     * Handles access denied exceptions by sending a 403 Forbidden error response.
     *
     * @param request               The HTTP request that caused the access denial.
     * @param response              The HTTP response where the error is written.
     * @param accessDeniedException The exception thrown when access is denied.
     * @throws IOException      If an input or output error occurs while handling the response.
     * @throws ServletException If a servlet-specific error occurs.
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.FORBIDDEN);
        errorMessage.addErrorMessage("Unauthorized to view this resource");
        errorResponseUtil.sendErrorResponse(response, errorMessage);
    }
}
