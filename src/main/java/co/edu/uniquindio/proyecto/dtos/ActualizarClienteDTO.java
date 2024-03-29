package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ActualizarClienteDTO(

        @NotBlank
        String codigo,
        @NotBlank
        String nombre,
        @NotBlank
        String nickname,
        @NotBlank
        String fotoPerfil,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String ciudad
) {
}
