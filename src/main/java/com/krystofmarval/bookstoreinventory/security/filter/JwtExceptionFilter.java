package com.krystofmarval.bookstoreinventory.security.filter;

import com.krystofmarval.bookstoreinventory.error.util.ErrorResponseUtil;
import com.krystofmarval.bookstoreinventory.model.payload.ErrorMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@AllArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private ErrorResponseUtil errorResponseUtil;

    // using this approach instead of Global Exception handling because we catch this before reaching controller logic
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            // clear sec context
            SecurityContextHolder.clearContext();

            //send error response
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED);
            errorMessage.addErrorMessage("Expired token");
            errorResponseUtil.sendErrorResponse(response, errorMessage);


        } catch (JwtException ex) {
            // Handle other JWT exceptions
            SecurityContextHolder.clearContext();


            //send error response
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED);
            errorMessage.addErrorMessage("Invalid token");
            errorResponseUtil.sendErrorResponse(response, errorMessage);


        }
    }

}

