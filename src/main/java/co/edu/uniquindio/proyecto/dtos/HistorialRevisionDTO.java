package co.edu.uniquindio.proyecto.dtos;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;

import java.time.LocalDateTime;

public record HistorialRevisionDTO(
        String descripcion,
        EstadoNegocio estadoNegocio,
        LocalDateTime fecha,
        String codigoModerador,
        String codigoNegocio
) {
}
