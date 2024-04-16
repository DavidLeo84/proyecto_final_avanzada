package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(

        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
