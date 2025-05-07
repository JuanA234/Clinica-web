package edu.unimagdalena.clinica.dto.Appointment;

import edu.unimagdalena.clinica.enumeration.Status;

import java.time.LocalDateTime;

public record ResponseAppointmentDTO(
        Long id,
        Long patientId,
        Long doctorId,
        Long consultRoomId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Status status
) {
}
