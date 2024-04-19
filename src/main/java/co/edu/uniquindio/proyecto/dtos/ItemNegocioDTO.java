package co.edu.uniquindio.proyecto.dtos;

import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ItemNegocioDTO(

        String codigo,
        @NotBlank
        @Size(max = 30)
        String nombre,
        @NotEmpty
        List<TipoNegocio> tiposNegocios
) {
}
