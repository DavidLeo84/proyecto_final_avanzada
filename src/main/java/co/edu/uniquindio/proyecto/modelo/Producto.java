package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.TipoProducto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document("productos")
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producto implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private String nombre;
    private int unidades;
    private float precio;

    private TipoProducto tipoProducto;
}
