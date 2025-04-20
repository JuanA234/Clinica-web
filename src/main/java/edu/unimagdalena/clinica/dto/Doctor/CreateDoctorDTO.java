package edu.unimagdalena.clinica.dto.Doctor;

import java.time.LocalTime;

public record CreateDoctorDTO(
        String fullname,
        String email,
        String specialty,
        LocalTime availableFrom,
        LocalTime availableTo
) {
}
