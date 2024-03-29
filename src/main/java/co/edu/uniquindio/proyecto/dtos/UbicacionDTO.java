package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.NotBlank;

public record UbicacionDTO(

        @NotBlank
        int longitud,
        @NotBlank
        int latitud
) {
}
