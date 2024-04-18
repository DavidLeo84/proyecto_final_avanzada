package co.edu.uniquindio.proyecto.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;

public record TokenDTO(
        String token
) {
}
