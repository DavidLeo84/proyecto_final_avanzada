package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "historial_revisiones")
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistorialRevision implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private String descripcion;
    private EstadoNegocio estadoNegocio;
    private LocalDateTime fecha;
    private String codigoModerador;

}
