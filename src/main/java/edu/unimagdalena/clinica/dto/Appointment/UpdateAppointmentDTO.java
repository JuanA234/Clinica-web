package edu.unimagdalena.clinica.dto.Appointment;

import edu.unimagdalena.clinica.enumeration.Status;

import java.time.LocalDateTime;

public record UpdateAppointmentDTO(
        LocalDateTime startTime,
        LocalDateTime endTime,
        Status status
) {
}
