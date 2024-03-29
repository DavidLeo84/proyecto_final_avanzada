package co.edu.uniquindio.proyecto.modelo.documentos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "comentarios")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comentario implements Serializable {

    private LocalDateTime fecha;
    private String mensaje;
    private String respuesta;
    private String codigoCliente;
    private String codigoNegocio;
    private int calificar; // se califica con estrellas que tienen valores enteros
}
