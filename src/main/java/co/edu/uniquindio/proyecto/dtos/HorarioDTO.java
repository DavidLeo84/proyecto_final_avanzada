package co.edu.uniquindio.proyecto.dtos;

import java.time.LocalTime;

public record HorarioDTO(

        String dia,
        LocalTime horaInicio,
        LocalTime horaFin
) {
}
