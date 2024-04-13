package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.Permiso;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "permisos")
@EqualsAndHashCode(callSuper = false)
public class Permisos {

    private String codigo;

    private Set<Permiso> permisos;

    private RolEntidad rolEntidad;
}
