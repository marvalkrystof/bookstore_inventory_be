/**
 * Custom implementation of {@link UserDetailsService} for user authentication.
 * <p>
 * This service is responsible for loading user details from the database
 * and converting them into Spring Security's {@link UserDetails} format.
 * </p>
 *
 * <b>Key Features:</b>
 * <ul>
 *   <li>Loads user details by username from the database.</li>
 *   <li>Converts user roles into Spring Security granted authorities.</li>
 *   <li>Ensures transactional consistency when fetching user details.</li>
 * </ul>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.service;

import com.krystofmarval.bookstoreinventory.model.User;
import com.krystofmarval.bookstoreinventory.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Repository for accessing user data.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Loads a user by their username and converts it into {@link UserDetails}.
     * <p>
     * This method retrieves the user from the database and maps their roles into granted authorities
     * for Spring Security to use in authentication and authorization.
     * </p>
     *
     * @param username The username of the user to retrieve.
     * @return A {@link UserDetails} instance representing the authenticated user.
     * @throws UsernameNotFoundException If the user is not found in the database.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPasswordHash(), getAuthorities(user));
    }

    /**
     * Retrieves the authorities (roles) assigned to a user.
     *
     * @param user The user whose roles are to be converted.
     * @return A collection of {@link GrantedAuthority} objects representing the user's roles.
     */
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getUserRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getName()))
                .collect(Collectors.toList());
    }
}
