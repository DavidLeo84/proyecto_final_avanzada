package co.edu.uniquindio.proyecto.dtos;

public record CambioPasswordDTO(
        String passwordNueva,
        String id,
        String token
) {
}
