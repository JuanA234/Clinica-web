package edu.unimagdalena.clinica.dto.Patient;

public record UpdatePatientDTO(
        String fullName,
        String email,
        String phone
) {
}
