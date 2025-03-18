/**
 * DTO (Data Transfer Object) for authentication requests.
 * <p>
 * This class is used to encapsulate the login credentials (username and password)
 * provided by users when attempting to authenticate.
 * </p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    /**
     * The username provided for authentication.
     */
    private String username;

    /**
     * The password provided for authentication.
     */
    private String password;
}
