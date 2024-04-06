package co.edu.uniquindio.proyecto.modelo;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Ubicacion {

    private int latitud;
    private int longitud;
}
