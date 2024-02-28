package co.edu.uniquindio.proyecto.modelo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@Document("clientes")
public class Cliente implements Serializable {

    @Id
    private String codigo;
    private String cedula;
    private String nombre;
    private String email;
    private List<String> telefonos;
}
