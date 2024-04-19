package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record DetalleComentarioDTO(

        //String codigo,
        @NotBlank
        String mensaje,
        @NotBlank
        String fechaMensaje,
        @NotBlank
        String respuesta,
//        String fechaRespuesta
        @Positive
        int meGusta

) {
}
