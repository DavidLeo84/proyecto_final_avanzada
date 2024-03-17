package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.modelo.Cuenta;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "moderadores")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Moderador extends Cuenta {

    @Id
    @EqualsAndHashCode.Include
    private int codigo;
}
