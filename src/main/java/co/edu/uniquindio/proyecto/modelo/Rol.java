package co.edu.uniquindio.proyecto.modelo;


import co.edu.uniquindio.proyecto.enums.PermisoEnum;
import co.edu.uniquindio.proyecto.enums.RolEnum;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Rol {

    private RolEnum nombreRol;
    private Set<PermisoEnum> permisos;
}
