package edu.unimagdalena.clinica.dto.ConsultRoom;

import jakarta.validation.constraints.NotBlank;

public record UpdateConsultRoomDTO(
        String name,
        String floor,
        String description
) {
}
