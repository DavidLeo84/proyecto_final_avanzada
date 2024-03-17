package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.modelo.Calificacion;
import co.edu.uniquindio.proyecto.modelo.Horario;
import co.edu.uniquindio.proyecto.modelo.Ubicacion;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;
import java.util.List;

@Document(collection = "negocios")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Negocio implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String codigo; //Se genera por medio de un metodo que crea los codigos consecutivos
    private String nombre;
    private Ubicacion ubicacion;
    private String descripcion;
    private String codigoCliente;
    private EstadoRegistro estado;
    private TipoNegocio tipoNegocio;
    private List<Horario> horarios;
    private List<String> telefonos;
    private List<HistorialRevision> historialRevisiones;
    private List<String> imagenes;
    private List<Comentario> comentarios;
    private List<Calificacion> calificaciones;


}
