/**
 * DTO (Data Transfer Object) for authentication responses.
 * <p>
 * This class is used to encapsulate the JWT (JSON Web Token) issued 
 * upon successful authentication.
 * </p>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    /**
     * The JWT (JSON Web Token) issued after authentication.
     */
    private String jwt;
}
