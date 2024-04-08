package co.edu.uniquindio.proyecto.modelo;

import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Horario implements Serializable {

    private String dia;
    private String horaInicio;
    private String horaFin;




}
