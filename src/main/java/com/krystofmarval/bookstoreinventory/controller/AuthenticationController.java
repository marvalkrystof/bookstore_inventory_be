package com.krystofmarval.bookstoreinventory.controller;

import com.krystofmarval.bookstoreinventory.dto.AuthenticationRequest;
import com.krystofmarval.bookstoreinventory.dto.AuthenticationResponse;
import com.krystofmarval.bookstoreinventory.security.JwtUtil;
import com.krystofmarval.bookstoreinventory.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller responsible for user authentication.
 * <p>
 * Handles login requests, authenticates users via Spring Security,
 * generates JWT (JSON Web Tokens), and provides tokens to clients upon successful authentication.
 * </p>
 *
 * <b>Endpoints:</b>
 * <ul>
 *   <li>POST /auth/login - Authenticates a user and returns a JWT token.</li>
 * </ul>
 *
 * <b>Security:</b>
 * <p>All requests require valid username/password credentials.</p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    /**
     * Spring Security's authentication manager responsible for authenticating the provided credentials.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Custom implementation of UserDetailsService that loads user details from the data source.
     */
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Utility class to generate and validate JWT tokens.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Authenticates a user based on provided username and password credentials.
     * Upon successful authentication, generates and returns a JWT token for authorization purposes.
     *
     * @param authRequest AuthenticationRequest object containing username and password.
     * @return ResponseEntity containing AuthenticationResponse with JWT token upon successful authentication.
     * @throws org.springframework.security.core.AuthenticationException if authentication fails.
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);


        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
