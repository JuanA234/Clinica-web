package edu.unimagdalena.clinica.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTORequest(
        @NotBlank(message = "El email no puede estar en blanco")
        @Email(message = "El email debe tener una direccion valida")
        String email,

        @NotBlank(message = "contraseña no puede estar en blanco")
        String password
) {
}
