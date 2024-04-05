package co.edu.uniquindio.proyecto.dtos;

import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.modelo.Horario;
import co.edu.uniquindio.proyecto.modelo.Ubicacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

public record DetalleNegocioDTO(

        @NotBlank
        @Size(max = 30)
        String nombre,
       /* @NotBlank
        TipoNegocio tipo,*/
        @NotBlank
        Ubicacion ubicacion,
        @NotBlank
        @Size(max = 200)
        String descripcion,
        @NotBlank
        List<Horario> horarios,
        @Size(max = 10)
        @NotEmpty
        Set<String> telefonos,
        @NotEmpty
        Set<String>imagenes
) {
}
