package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteServicioImpl clienteServicio;
    private final NegocioServicioImpl negocioServicio;

    @PutMapping("/editar-perfil")
    public ResponseEntity<MensajeDTO<String>> actualizarCliente(@Valid @RequestBody
                                                                ActualizarClienteDTO actualizarClienteDTO) throws Exception {

        Cliente cliente = clienteServicio.actualizarCliente(actualizarClienteDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El cliente fue actualizado correctamente"));
    }

    @DeleteMapping("/eliminar/{codigo}")
    public ResponseEntity<MensajeDTO<String>> eliminarCuenta(@PathVariable String codigo) throws Exception {

        clienteServicio.eliminarCuenta(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El cliente fue eliminado correctamente"));
    }

    @GetMapping("/obtener/{codigo}")
    public ResponseEntity<MensajeDTO<DetalleClienteDTO>> obtenerCliente(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(
                new MensajeDTO<>(false, clienteServicio.obtenerUsuario(codigo)));
    }

    @PutMapping("/cambio-password")
    public ResponseEntity<MensajeDTO<String>> cambiarPassword(@Valid @RequestBody CambioPasswordDTO cambioPasswordDTO) throws Exception {

        clienteServicio.cambiarPassword(cambioPasswordDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El password se cambio con éxito"));
    }

    @PostMapping("/crear-negocio")
    public ResponseEntity<MensajeDTO<String>> crearNegocio(@Valid @RequestBody RegistroNegocioDTO negocioDTO) throws Exception {

        negocioServicio.crearNegocio(negocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "EL negocio fue creado con éxito"));
    }

    @PutMapping("/actualizar-negocio")
    public ResponseEntity<MensajeDTO<String>> actualizarNegocio(@Valid @RequestBody
                                                                    ActualizarNegocioDTO negocioDTO) throws Exception {
        negocioServicio.actualizarNegocio(negocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Se ha actualizado el negocio correctamente"));
    }

    @DeleteMapping("/eliminar-negocio/{codigo}")
    public ResponseEntity<MensajeDTO<String>> eliminarNegocio(@PathVariable String codigo) throws Exception {

        negocioServicio.eliminarNegocio(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Se ha eliminado el negocio correctamente"));
    }

    @GetMapping("/negocios-propietario/{codigo}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listarNegociosPropietario(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarNegociosPropietario(codigo)));
    }






}
