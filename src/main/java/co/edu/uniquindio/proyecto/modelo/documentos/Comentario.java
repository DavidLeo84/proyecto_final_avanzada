package co.edu.uniquindio.proyecto.modelo.documentos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comentarios")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comentario implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private String codigoCliente;
    private String codigoNegocio;
    private String mensaje;
    private String respuesta;
    private String fechaMensaje;
    private String fechaRespuesta;
    private List<Integer> meGusta;
}
