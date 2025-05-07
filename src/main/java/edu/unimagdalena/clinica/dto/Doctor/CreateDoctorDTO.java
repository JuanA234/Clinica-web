package edu.unimagdalena.clinica.dto.Doctor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record CreateDoctorDTO(
        @NotBlank String fullname,
        @NotBlank String email,
        @NotBlank String specialty,
        @NotNull LocalTime availableFrom,
        @NotNull LocalTime availableTo,
        @NotBlank String password
) {
}
