package edu.unimagdalena.clinica.dto.Doctor;

import org.springframework.cglib.core.Local;

import java.time.LocalTime;

public record ResponseDoctorDTO(
        Long id,
        String fullname,
        String email,
        String specialty,
        LocalTime availableFrom,
        Local availableTo
) {
}
