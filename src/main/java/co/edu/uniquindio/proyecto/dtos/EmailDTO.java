package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmailDTO(

        @NotBlank
        @Email
        String destinatario,
        @NotBlank
        String asunto,
        @NotBlank
        @Size(max = 200)
        String mensaje
) {
}
