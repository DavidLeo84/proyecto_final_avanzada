package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.NotBlank;

public record TokenDTO(

        @NotBlank
        String token
) {
}
