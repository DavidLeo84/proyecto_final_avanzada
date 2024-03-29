package co.edu.uniquindio.proyecto.dtos;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record RegistroHistorialDTO(

        @NotBlank
        String codigoModerador,
        @NotBlank
        String descripcion,
        @NotBlank
        LocalDateTime fecha,
        @NotBlank
        EstadoNegocio estado
) {
}
