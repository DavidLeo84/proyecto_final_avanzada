package co.edu.uniquindio.proyecto.dtos;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

public record DetalleRevisionDTO(

        @NotBlank
        String descripcion,
        @NotBlank
        EstadoNegocio estadoNegocio,
        @FutureOrPresent
        String fecha

) {
}
