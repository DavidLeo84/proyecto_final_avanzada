package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
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
    private String codigo;
    private String nombre;
    private Ubicacion ubicacion;
    private String descripcion;
    private String codigoCliente;
    private EstadoRegistro estado;
    private TipoNegocio tipoNegocio;
    private List<Horario> horarios;
    private List<Telefono> telefonos;
    private List<HistorialRevision> historialRevisiones;
    private List<String> imagenes;


}
