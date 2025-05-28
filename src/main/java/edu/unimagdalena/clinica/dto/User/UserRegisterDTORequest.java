package edu.unimagdalena.clinica.dto.User;

import edu.unimagdalena.clinica.enumeration.RolesEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterDTORequest(
        @NotBlank String user,
        @NotBlank String email,
        @NotBlank String password,
        @NotNull RolesEnum role
        ) {
}
