package edu.unimagdalena.clinica.dto.Patient;

public record ResponsePatientDTO(
        Long id,
        String fullName,
        String email,
        String phone
) {
}
