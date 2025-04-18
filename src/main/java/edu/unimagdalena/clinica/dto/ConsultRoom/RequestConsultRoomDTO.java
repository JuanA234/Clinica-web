package edu.unimagdalena.clinica.dto.ConsultRoom;

import jakarta.validation.constraints.NotBlank;

public record RequestConsultRoomDTO(
        @NotBlank String name,
        @NotBlank String floor,
        String description
) {
}
