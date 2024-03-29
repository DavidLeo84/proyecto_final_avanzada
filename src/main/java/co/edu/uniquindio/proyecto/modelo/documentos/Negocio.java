package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.modelo.Calificacion;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.Horario;
import co.edu.uniquindio.proyecto.modelo.Ubicacion;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Document(collection = "negocios")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class Negocio implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private String codigoCliente;
    private String nombre;
    private Ubicacion ubicacion;
    private String descripcion;
    private EstadoNegocio estado;
    private TipoNegocio tipoNegocio;
    private Set<Horario> horarios;
    private Set<String> telefonos;
    private Set<HistorialRevision> historialRevisiones;
    private Set<String> imagenes;
    private Set<Comentario> comentarios;
    private Set<Calificacion> calificaciones;
}
