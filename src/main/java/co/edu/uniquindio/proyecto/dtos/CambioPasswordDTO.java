package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.NotBlank;

public record CambioPasswordDTO(

        @NotBlank
        String passwordNueva,
        @NotBlank
        String codigoUsuario
        //String token
) {
}
