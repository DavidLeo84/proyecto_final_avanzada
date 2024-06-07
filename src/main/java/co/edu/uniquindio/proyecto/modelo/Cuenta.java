package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.RolEnum;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class Cuenta implements Serializable {

    private String email;
    private String password;
    private EstadoRegistro estadoRegistro;
    private Rol rol;
    private String nombre;

    public Cuenta(String email, String password, EstadoRegistro estadoRegistro, Rol rol, String nombre) {
        this.email = email;
        this.password = password;
        this.estadoRegistro = estadoRegistro;
        this.rol = rol;
        this.nombre = nombre;
    }
}
