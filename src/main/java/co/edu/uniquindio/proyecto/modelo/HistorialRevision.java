package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistorialRevision implements Serializable {

    private String descripcion;
    private EstadoNegocio estadoNegocio;
    private String fecha;
    private String codigoModerador;
    private String codigoNegocio;

}
