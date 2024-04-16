package co.edu.uniquindio.proyecto.dtos;

public record MensajeDTO<T>(

        boolean error,
        T mensaje
) {
}
