package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.Rol;
import co.edu.uniquindio.proyecto.modelo.Cuenta;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "moderadores")
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Moderador extends Cuenta {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;

    @Builder
    public Moderador(String email, String password, EstadoRegistro estadoRegistro, Rol rol, String codigo) {
        super(email, password, EstadoRegistro.ACTIVO, Rol.ADMINISTRADOR);
        this.codigo = codigo;
    }
}
