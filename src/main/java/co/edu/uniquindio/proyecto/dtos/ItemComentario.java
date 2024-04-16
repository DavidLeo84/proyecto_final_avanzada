package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.NotBlank;

public record ItemComentario(
        @NotBlank
        String mensaje
) {
}
