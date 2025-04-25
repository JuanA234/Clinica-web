package edu.unimagdalena.clinica.dto.MedicalRecord;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateMedicalRecordDTO(
        @NotNull Long appointmentId,
        @NotNull Long patientId,
        @NotBlank String diagnosis,
        String notes,
        @NotNull LocalDateTime createdAt
) {
}
