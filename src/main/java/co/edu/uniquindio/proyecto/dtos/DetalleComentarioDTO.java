package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.Positive;

public record DetalleComentarioDTO(

        //String codigo,
        String mensaje,
        String fechaMensaje,
        String respuesta,
//        String fechaRespuesta
        @Positive
        int meGusta

) {
}
