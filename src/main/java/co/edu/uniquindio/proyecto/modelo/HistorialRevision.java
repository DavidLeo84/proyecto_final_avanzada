package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HistorialRevisiones implements Serializable {

    private String descripcion;
    private EstadoNegocio estadoNegocio;
    private LocalDateTime fecha;
    private String codigoModerador;

}
