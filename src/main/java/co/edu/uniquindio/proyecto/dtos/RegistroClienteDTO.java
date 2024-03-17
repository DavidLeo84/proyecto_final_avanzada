package co.edu.uniquindio.proyecto.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record RegistroClienteDTO(

        @NotBlank
        @Length(max = 60)
        String nombre,
        @NotBlank
        String fotoPerfil,
        @NotBlank
        String nickname,
        @NotBlank
        @Email
        @Length(max = 30)
        String email,
        @NotBlank
        String password,
        @NotBlank
        @Length(max = 30)
        String ciudad,
        List<String> favoritos,
        List<String> negocios
) {
}
