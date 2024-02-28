package co.edu.uniquindio.proyecto.modelo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pago implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private LocalDateTime fecha;
    private float totalPagado;
    private String estado;
    private String metodoPago;
}
