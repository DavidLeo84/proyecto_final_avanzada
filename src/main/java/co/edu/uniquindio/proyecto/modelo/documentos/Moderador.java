package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.RolEnum;
import co.edu.uniquindio.proyecto.modelo.Cuenta;
import co.edu.uniquindio.proyecto.modelo.Rol;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "moderadores")
public class Moderador extends Cuenta {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private String nombre;

    @Builder
    public Moderador(String email, String password, EstadoRegistro estadoRegistro, Rol rol,
                     boolean isEnabled, boolean accountNoExpired, boolean accountNoLocked,
                     boolean credentialNoExpired, String codigo, String nombre) {
        super(email, password, estadoRegistro, rol, isEnabled, accountNoExpired, accountNoLocked, credentialNoExpired);
        this.codigo = codigo;
        this.nombre = nombre;
    }
}
