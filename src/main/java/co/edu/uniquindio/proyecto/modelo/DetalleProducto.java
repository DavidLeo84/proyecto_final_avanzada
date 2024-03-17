package co.edu.uniquindio.proyecto.modelo;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetalleProducto implements Serializable {

    private String codigoProducto;
    private float precio;
    private int cantidad;

    @Builder
    public DetalleProducto(String codigoProducto, float precio, int cantidad) {
        this.codigoProducto = codigoProducto;
        this.precio = precio;
        this.cantidad = cantidad;
    }
}

