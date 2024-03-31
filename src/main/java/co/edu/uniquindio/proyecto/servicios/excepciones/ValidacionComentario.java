package co.edu.uniquindio.proyecto.servicios.excepciones;

import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ValidacionComentario {

    private final ComentarioRepo comentarioRepo;

    public void validarCalificacion(int calificacion) throws Exception {

        if (calificacion < 0 || calificacion > 5) {
            throw new ResourceInvalidException("Valor no válido para calificar sitio");
        }
    }

    public String validarFecha(LocalDateTime fecha) throws Exception {

        if (fecha.isAfter(LocalDateTime.now())) {
            throw new ResourceInvalidException("Fecha no válida");
        }
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a",Locale.ENGLISH);
        String fechaFormateada = formatoFecha.format(fecha);
//        String fechaFormateada = formatoFecha.format(fecha);
//        LocalDateTime fecha1 = LocalDateTime.parse(fechaFormateada, formatoFecha);
        return fechaFormateada;
    }
}
