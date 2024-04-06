package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.repository.ExistsQuery;

import java.time.LocalDateTime;

public record RespuestaComentarioDTO(

        @NotBlank
        String codigoCliente,
        @NotBlank
        String codigoNegocio,
        @NotBlank
        @Size(max = 200)
        @ExistsQuery(value = "false")
        @Pattern(regexp = "\".*[!@#$%^&*,/\\\\\\\\()¿~_'<>:;+.=?-].*\"")
        String respuesta,
        @FutureOrPresent
        LocalDateTime fecha,
        @Positive
        int calificacion
) {
}
