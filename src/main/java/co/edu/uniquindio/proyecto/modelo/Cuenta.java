package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.RolEnum;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ToString
@NoArgsConstructor
@Data
public class Cuenta implements Serializable {

    private String email;
    private String password;
    private EstadoRegistro estadoRegistro;
    private Rol rol;

    private boolean isEnabled;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialNoExpired;

    public Cuenta(String email, String password, EstadoRegistro estadoRegistro, Rol rol, boolean isEnabled,
                  boolean accountNoExpired, boolean accountNoLocked, boolean credentialNoExpired) {
        this.email = email;
        this.password = password;
        this.estadoRegistro = estadoRegistro;
        this.rol = rol;
        this.isEnabled = isEnabled;
        this.accountNoExpired = accountNoExpired;
        this.accountNoLocked = accountNoLocked;
        this.credentialNoExpired = credentialNoExpired;
    }
}
