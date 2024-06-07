package co.edu.uniquindio.proyecto.dtos;

import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.enums.ValorCalificar;
import co.edu.uniquindio.proyecto.modelo.Horario;
import co.edu.uniquindio.proyecto.modelo.Ubicacion;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;

public record DetalleNegocioDTO(

        @NotNull
        String codigo,
        @NotBlank
        @Size(max = 30)
        String nombre,
        @NotBlank
        List<String> tipoNegocios,
        @NotBlank
        Ubicacion ubicacion,
        @NotBlank
        @Size(max = 200)
        String descripcion,
        @NotBlank
        @Positive
        int calificacion,
        @NotBlank
        List<Horario> horarios,
        @Size(max = 10)
        @NotEmpty
        Set<String> telefonos,
        @NotEmpty
        Set<String>imagenes
) {
}
