package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.ValorCalificar;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Calificacion implements Serializable {

    private ValorCalificar calificar;
}
