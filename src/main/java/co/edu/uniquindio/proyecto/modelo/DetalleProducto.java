package co.edu.uniquindio.proyecto.modelo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetalleProducto implements Serializable {

    private String codigoProducto;
    private float precio;
    private int cantidad;
}
