package edu.unimagdalena.clinica.dto.Doctor;

import java.time.LocalTime;

public record UpdateDoctorDTO(
        String fullname,
        String email,
        String specialty,
        LocalTime availableFrom,
        LocalTime availableTo
) {
}
