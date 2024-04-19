package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ItemComentarioDTO(

        @NotBlank
        String fecha,
        @NotBlank
        String mensaje

) {
}
