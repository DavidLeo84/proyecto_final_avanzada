package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.lang.annotation.Inherited;

@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "cuentas")
@Data
public class Cuenta implements Serializable {

    private String nombre;
    private String password;
    private String email;
    private EstadoRegistro estadoRegistro;


    public Cuenta(String nombre, String password, String email, EstadoRegistro estadoRegistro) {
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.estadoRegistro = estadoRegistro;
    }
}
