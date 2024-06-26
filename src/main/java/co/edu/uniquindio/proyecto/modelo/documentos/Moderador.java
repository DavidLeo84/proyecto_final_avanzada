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

    @Builder
    public Moderador(String email, String password, EstadoRegistro estadoRegistro, Rol rol, String nombre,
                     String codigo) {
        super(email, password, estadoRegistro, rol, nombre);
        this.codigo = codigo;
    }
}
