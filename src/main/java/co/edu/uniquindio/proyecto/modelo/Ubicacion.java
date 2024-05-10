package co.edu.uniquindio.proyecto.modelo;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Ubicacion {

    private Double latitud;
    private Double longitud;
}
