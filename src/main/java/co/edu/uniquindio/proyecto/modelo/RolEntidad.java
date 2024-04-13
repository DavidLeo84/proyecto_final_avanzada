package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.Rol;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "roles")
@EqualsAndHashCode(callSuper = false)
public class RolEntidad {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private Rol rol;

    private Set<String> listaPermisos;

}
