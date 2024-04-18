package co.edu.uniquindio.proyecto.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"email", "mensaje", "token", "status"})
public record AuthResponse(

        String email,
        String mensaje,
        String token,
        boolean status

) {
}
