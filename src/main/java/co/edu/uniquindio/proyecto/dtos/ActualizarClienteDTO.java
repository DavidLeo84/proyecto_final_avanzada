package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActualizarClienteDTO(

        @NotBlank
        String codigo,
        @NotBlank
        @Size(max = 50)
        String nombre,
        @NotBlank
        String fotoPerfil,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(max = 30)
        String ciudad
) {
}
