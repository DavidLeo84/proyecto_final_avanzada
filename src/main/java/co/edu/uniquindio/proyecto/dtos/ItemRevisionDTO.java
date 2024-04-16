package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

public record ItemRevisionDTO(

        @NotBlank
        String codigoNegocio,
        @FutureOrPresent
        String fecha
) {
}
