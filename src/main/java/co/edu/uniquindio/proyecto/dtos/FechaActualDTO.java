package co.edu.uniquindio.proyecto.dtos;

import co.edu.uniquindio.proyecto.enums.DiaSemana;
import jakarta.validation.constraints.NotBlank;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record FechaActualDTO(
        DayOfWeek dia,
        @NotBlank
        LocalTime hora
) {
}
