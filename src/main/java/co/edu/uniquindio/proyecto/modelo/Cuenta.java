package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.Rol;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.lang.annotation.Inherited;

@ToString
@NoArgsConstructor
@Data
public class Cuenta implements Serializable {

    private String email;
    private String password;
    private EstadoRegistro estadoRegistro;
    private Rol rol;

    public Cuenta(String email, String password, EstadoRegistro estadoRegistro,
                  Rol rol) {
        this.email = email;
        this.password = password;
        this.estadoRegistro = EstadoRegistro.ACTIVO;
        this.rol = Rol.USUARIO;
    }
}
