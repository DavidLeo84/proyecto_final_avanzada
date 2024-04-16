package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.DiaSemana;
import lombok.*;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Horario implements Serializable {

    private DayOfWeek dia;
    private String horaInicio;
    private String horaFin;
}
