package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.enums.ValorCalificar;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.ComentarioServicioImpl;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clientes")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

    private final ClienteServicioImpl clienteServicio;

    @PutMapping("/editar-perfil")
    public ResponseEntity<MensajeDTO<String>> actualizarCliente(@Valid @RequestBody
                                                                ActualizarClienteDTO actualizarClienteDTO) throws Exception {

        Cliente cliente = clienteServicio.actualizarCliente(actualizarClienteDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El cliente fue actualizado correctamente"));
    }

    @DeleteMapping("/eliminar-cliente/{codigoCliente}")
    public ResponseEntity<MensajeDTO<String>> eliminarCuenta(@PathVariable String codigoCliente) throws Exception {

        clienteServicio.eliminarCuenta(codigoCliente);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El cliente fue eliminado correctamente"));
    }

    @GetMapping("/obtener-cliente/{codigoCliente}")
    public ResponseEntity<MensajeDTO<DetalleClienteDTO>> obtenerCliente(@PathVariable String codigoCliente) throws Exception {

        return ResponseEntity.ok().body(
                new MensajeDTO<>(false, clienteServicio.obtenerUsuario(codigoCliente)));
    }

    @PutMapping("/cambio-password")
    public ResponseEntity<MensajeDTO<String>> cambiarPassword(@Valid @RequestBody CambioPasswordDTO cambioPasswordDTO) throws Exception {

        clienteServicio.cambiarPassword(cambioPasswordDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El password se cambio con éxito"));
    }




}
