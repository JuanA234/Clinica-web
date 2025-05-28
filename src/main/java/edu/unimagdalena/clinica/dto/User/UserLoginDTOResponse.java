package edu.unimagdalena.clinica.dto.User;

import edu.unimagdalena.clinica.enumeration.RolesEnum;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTOResponse(
        String role,
        String token
) {
}
