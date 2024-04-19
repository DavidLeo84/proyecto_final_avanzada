package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;

public record HorarioDTO(

        @NotBlank
        String dia,
        @NotBlank
        LocalTime horaInicio,
        @NotBlank
        LocalTime horaFin
) {
}
