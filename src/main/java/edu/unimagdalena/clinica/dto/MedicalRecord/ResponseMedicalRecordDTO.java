package edu.unimagdalena.clinica.dto.MedicalRecord;


import java.time.LocalDateTime;

public record ResponseMedicalRecordDTO(
        Long id,
        Long appointmentId,
        Long patientId,
        String diagnosis,
        String notes,
        LocalDateTime createdAt
) {
}
