package edu.unimagdalena.clinica.dto.Appointment;



import edu.unimagdalena.clinica.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateAppointmentDTO(
        @NotNull Long patientId,
        @NotNull Long doctorId,
        @NotNull Long consultRoomId,
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime

) {
}
