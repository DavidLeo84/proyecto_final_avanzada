package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.enums.ValorCalificar;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.ComentarioServicioImpl;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteServicioImpl clienteServicio;
    private final NegocioServicioImpl negocioServicio;
    private final ComentarioServicioImpl comentarioServicio;

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

    @GetMapping("/obtener-negocio/{codigoNegocio}")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> obtenerNegocio(@PathVariable String codigoNegocio,
                                                                        @Valid @RequestBody ValorCalificar calificacion) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.obtenerNegocio(codigoNegocio, calificacion)));
    }

    @GetMapping("/negocios-propietario/{codigo}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listarNegociosPropietario(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarNegociosPropietario(codigo)));
    }

    @PutMapping("/guardar-recomendado/{codigoNegocio}/{codigoCliente}")
    public ResponseEntity<MensajeDTO<String>> guardarRecomendado(@PathVariable String codigoNegocio,
                                                                 @PathVariable String codigoCliente) throws Exception {
        negocioServicio.guardarRecomendado(codigoNegocio, codigoCliente);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Has recomendado este negocio"));
    }

    @GetMapping("/obtener-recomendado/{codigoCliente}")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> obtenerRecomendado(@PathVariable String codigoCliente,
                                                                            @Valid @RequestBody ValorCalificar calificacion) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.obtenerRecomendado(codigoCliente, calificacion)));
    }

    @DeleteMapping("/borrar-recomendado/{codigoNegocio}/{codigoCliente}")
    public ResponseEntity<MensajeDTO<String>> eliminarRecomendado(@PathVariable String codigoNegocio,
                                                                  @PathVariable String codigoCliente) throws Exception {
        negocioServicio.eliminarNegocioRecomendado(codigoNegocio, codigoCliente);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,
                "El negocio fue eliminado de su lista de recomendados con éxito"));
    }

    @GetMapping("/listar-recomendados-cliente/{codigo}")
    public ResponseEntity<MensajeDTO<Set<ItemNegocioDTO>>> listarRecomendadosCliente(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarRecomendadosCliente(codigo)));
    }

    @GetMapping("/listar-recomendados")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listaNegociosRecomendadosPorClientes() throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listaNegociosRecomendadosPorClientes()));
    }

    @GetMapping("/obtener-revision")
    public ResponseEntity<MensajeDTO<DetalleRevisionDTO>> obtenerRevision(@Valid @RequestBody
                                                                          ItemRevisionDTO item) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.obtenerRevision(item)));
    }

    @GetMapping("/listar-revisiones/{codigo}")
    public ResponseEntity<MensajeDTO<List<ItemRevisionDTO>>> listarRevisiones(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarRevisiones(codigo)));
    }

    @PutMapping("/guardar-favorito/{codigoNegocio}/{codigoCliente}")
    public ResponseEntity<MensajeDTO<String>> guardarFavorito(@PathVariable String codigoNegocio,
                                                              @PathVariable String codigoCliente) throws Exception {
        negocioServicio.guardarNegocioFavorito(codigoNegocio, codigoCliente);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Has guardado este negocio entre tus favoritos"));
    }

    @DeleteMapping("/borrar-favorito/{codigoNegocio}/{codigoCliente}")
    public ResponseEntity<MensajeDTO<String>> eliminarFavorito(@PathVariable String codigoNegocio,
                                                               @PathVariable String codigoCliente) throws Exception {
        negocioServicio.eliminarNegocioFavorito(codigoNegocio, codigoCliente);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,
                "El negocio fue eliminado de su lista de favoritos con éxito"));
    }

    @GetMapping("/listar-favoritos-cliente/{codigo}")
    public ResponseEntity<MensajeDTO<Set<ItemNegocioDTO>>> listarFavoritos(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarFavoritos(codigo)));
    }

    @GetMapping("/obtener-favorito/{codigoCliente}")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> obtenerFavorito(@PathVariable String codigo,
                                                                         @Valid @RequestBody ValorCalificar calificacion) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.obtenerFavorito(codigo, calificacion)));
    }

    @GetMapping("/esta-disponible/{codigo}")
    public ResponseEntity<MensajeDTO<String>> determinarDisponibilidadNegocio(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.determinarDisponibilidadNegocio(codigo)));
    }

    @GetMapping("/buscar-nombre/{codigo}")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> buscarNegocioPorNombre(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.buscarNegocioPorNombre(codigo)));
    }

    @GetMapping("/lista-negocios-abiertos/{tipo}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listarNegociosAbiertosPorTipoSegunHora(@PathVariable
                                                                                                   TipoNegocio tipo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarNegociosAbiertosPorTipoSegunHora(tipo)));
    }


    @PostMapping("/crer-comentario")
    public ResponseEntity<MensajeDTO<String>> crearComentario(@Valid @RequestBody
                                                              RegistroComentarioDTO comentarioDTO) throws Exception {

        comentarioServicio.crearComentario(comentarioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Se ha agregado un nuevo comentario"));
    }

    @PostMapping("/responder-comentario")
    public ResponseEntity<MensajeDTO<String>> responderComentario(@Valid @RequestBody
                                                                  RegistroRespuestaComentarioDTO comentarioDTO) throws Exception {

        comentarioServicio.responderComentario(comentarioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Se ha agregado la respuesta al comentario"));
    }

    @GetMapping("/listar-comentarios/{codigo}")
    public ResponseEntity<MensajeDTO<List<ItemComentarioDTO>>> listarComentariosNegocio(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, comentarioServicio.listarComentariosNegocio(codigo)));
    }

    @GetMapping("/obtener-comentario/{codigo}")
    public ResponseEntity<MensajeDTO<DetalleComentarioDTO>> obtenerComentarioNegocio(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, comentarioServicio.obtenerComentarioNegocio(codigo)));
    }

    @PutMapping("/aprobar-comentario/{codigoComentario}/{codigoCliente}")
    public ResponseEntity<MensajeDTO<String>> aprobarComentario(@PathVariable String codigoComentario,
                                                                @PathVariable String codigoCliente) throws Exception {
        comentarioServicio.aprobarComentario(codigoComentario, codigoCliente);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Te gustó este comentario"));
    }

}
