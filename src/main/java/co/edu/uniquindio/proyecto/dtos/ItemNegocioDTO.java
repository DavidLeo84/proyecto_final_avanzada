package co.edu.uniquindio.proyecto.dtos;

import co.edu.uniquindio.proyecto.modelo.Ubicacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

public record ItemNegocioDTO(

        String codigoNegocio,
        @NotBlank
        @Size(max = 30)
        String nombre,
        @NotEmpty
        List<String> tipoNegocios,
        Ubicacion ubicacion,
        Set<String> imagenes
) {
}
