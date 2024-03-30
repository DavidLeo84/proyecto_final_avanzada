package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;

public record RegistroComentarioDTO(

        @NotBlank
        String codigoCliente,
        @NotBlank
        String codigoNegocio,
        @NotBlank
        @Size(max = 200)
        @ExistsQuery(value = "false")
        @Pattern(regexp = "\".*[!@#$%^&*,/\\\\\\\\()¿~_'<>:;+.=?-].*\"")
        String mensaje,
        @NotBlank
        @Size(max = 200)
        String respuesta,
        @FutureOrPresent
        LocalDateTime fecha

) {
}
