package edu.unimagdalena.clinica.dto.Appointment;

import edu.unimagdalena.clinica.entity.Status;

import java.time.LocalDateTime;

public record UpdateAppointmentDTO(
        Long patientId,
        Long doctorId,
        Long consultRoomId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Status status
) {
}
