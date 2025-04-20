package edu.unimagdalena.clinica.dto.ConsultRoom;

import jakarta.validation.constraints.NotBlank;

public record CreateConsultRoomDTO(
        @NotBlank String name,
        @NotBlank String floor,
        String description
) {
}
