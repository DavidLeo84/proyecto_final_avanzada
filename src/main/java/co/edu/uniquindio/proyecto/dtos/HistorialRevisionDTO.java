package co.edu.uniquindio.proyecto.dtos;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record HistorialRevisionDTO(
        @NotBlank
        String descripcion,
        @NotBlank
        EstadoNegocio estadoNegocio,
        //LocalDateTime fecha,
        @NotBlank
        String codigoModerador,
        @NotBlank
        String codigoNegocio
) {
}
