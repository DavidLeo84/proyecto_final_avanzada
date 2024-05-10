package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.repository.ExistsQuery;

import java.time.LocalDateTime;

public record RegistroRespuestaComentarioDTO(

        @NotBlank
        String codigoComentario,
        @NotBlank
        String codigoCliente,
        @NotBlank
        String codigoNegocio,
        @NotBlank
        @Size(max = 200)
        String mensaje,

        String fechaMensaje,
        @NotBlank
        @Size(max = 200)
        String respuesta,

        LocalDateTime fechaRespuesta

) {
}
