package edu.unimagdalena.clinica.dto.ConsultRoom;

import jakarta.validation.constraints.NotBlank;

public record ResponseConsultRoomDTO(
        Long id,
        @NotBlank String name,
        @NotBlank String floor,
        String description
) {
}
