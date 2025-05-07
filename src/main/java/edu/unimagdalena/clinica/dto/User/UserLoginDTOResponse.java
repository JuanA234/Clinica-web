package edu.unimagdalena.clinica.dto.User;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTOResponse(
        String user,
        String password,
        String role,
        String token
) {
}
