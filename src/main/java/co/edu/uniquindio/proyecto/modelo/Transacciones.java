package co.edu.uniquindio.proyecto.modelo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document("transacciones")
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transacciones implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;

    private List<DetalleProducto> detalleProductos;
    private String cliente;
    private LocalDateTime fecha;
    private Pago pago;
}


