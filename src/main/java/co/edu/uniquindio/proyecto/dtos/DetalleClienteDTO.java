package co.edu.uniquindio.proyecto.dtos;

import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DetalleClienteDTO(

        @NotBlank
        @Size(max = 50)
        String nombre,
        @NotBlank
        @Size(max = 20)
        String ciudad,
        String fotoPerfil


) {
/*
    public DetalleClienteDTO(Cliente cliente){
        this(
                cliente.getNombre();
                cliente.getCiudad();
                cliente.getFotoPerfil();
        );
    }

 */


}