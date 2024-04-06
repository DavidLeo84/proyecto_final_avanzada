package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.Horario;
import co.edu.uniquindio.proyecto.modelo.Ubicacion;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "negocios")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Negocio implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private String codigoCliente;
    private String nombre;
    private Ubicacion ubicacion;
    private String descripcion;
    private EstadoRegistro estadoRegistro;
    private TipoNegocio tipoNegocio;
    private List<Horario> horarios;
    private Set<String> telefonos;
    private List<HistorialRevision> historialRevisiones;
    private Set<String> imagenes;
    private List<String> calificaciones; // se califica con estrellas que tienen valores enteros
}
