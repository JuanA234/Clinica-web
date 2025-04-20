package edu.unimagdalena.clinica.dto.Patient;

import jakarta.validation.constraints.NotBlank;

public record CreatePatientDTO(
        @NotBlank String fullName,
        @NotBlank String email,
        String phone
) {
}
