/**
 * Security configuration for the bookstore inventory system.
 * <p>
 * This class configures security settings, including authentication, authorization,
 * password encoding, and JWT-based request filtering.
 * </p>
 *
 * <b>Key Features:</b>
 * <ul>
 *   <li>Disables CSRF protection for stateless authentication.</li>
 *   <li>Configures JWT filters for authentication and exception handling.</li>
 *   <li>Enforces stateless session management.</li>
 *   <li>Sets up custom access denied and authentication entry point handlers.</li>
 * </ul>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.security;

import com.krystofmarval.bookstoreinventory.error.handler.CustomAccessDeniedHandler;
import com.krystofmarval.bookstoreinventory.security.entrypoint.CustomAuthenticationEntryPoint;
import com.krystofmarval.bookstoreinventory.security.filter.JwtExceptionFilter;
import com.krystofmarval.bookstoreinventory.security.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * JWT filter for processing authentication tokens.
     */
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * JWT exception filter for handling token-related errors.
     */
    @Autowired
    private JwtExceptionFilter jwtExceptionFilter;

    /**
     * Custom handler for handling access denied exceptions.
     */
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    /**
     * Custom authentication entry point for handling unauthorized access attempts.
     */
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /**
     * Configures the security filter chain.
     * <p>
     * This method sets up authentication rules, disables CSRF protection,
     * configures session management, and adds JWT authentication filters.
     * </p>
     *
     * @param http The {@link HttpSecurity} configuration object.
     * @return The configured {@link SecurityFilterChain}.
     * @throws Exception If an error occurs while configuring security.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Do not need CSRF protection because we are using JWT
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/login").permitAll() // Allow login endpoint
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                );

        http.addFilterBefore(jwtExceptionFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Provides an authentication manager bean.
     *
     * @param authenticationConfiguration The authentication configuration.
     * @return The {@link AuthenticationManager} instance.
     * @throws Exception If an error occurs while retrieving the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Provides a password encoder bean.
     * <p>
     * Uses {@link BCryptPasswordEncoder} for secure password hashing.
     * </p>
     *
     * @return The {@link PasswordEncoder} instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
