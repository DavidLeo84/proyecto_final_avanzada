package co.edu.uniquindio.proyecto.controller;


import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.enums.ValorCalificar;
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
@RequestMapping("/api/negocios")
@SecurityRequirement(name = "bearerAuth")
public class NegocioController {

    private final NegocioServicioImpl negocioServicio;

    @PostMapping("/crear-negocio")
    public ResponseEntity<MensajeDTO<String>> crearNegocio(@Valid @RequestBody RegistroNegocioDTO negocioDTO) throws Exception {

        negocioServicio.crearNegocio(negocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El negocio fue creado con éxito"));
    }

    @PutMapping("/actualizar-negocio")
    public ResponseEntity<MensajeDTO<String>> actualizarNegocio(@Valid @RequestBody
                                                                ActualizarNegocioDTO negocioDTO) throws Exception {
        negocioServicio.actualizarNegocio(negocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Se ha actualizado el negocio correctamente"));
    }

    @DeleteMapping("/eliminar-negocio/{codigoNegocio}")
    public ResponseEntity<MensajeDTO<String>> eliminarNegocio(@PathVariable String codigoNegocio) throws Exception {

        negocioServicio.eliminarNegocio(codigoNegocio);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Se ha eliminado el negocio correctamente"));
    }

    @GetMapping("/obtener-negocio/{codigoNegocio}")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> obtenerNegocio(@PathVariable String codigoNegocio) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.obtenerNegocio(codigoNegocio)));
    }

    @GetMapping("/negocios-propietario/{codigoCliente}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listarNegociosPropietario(@PathVariable String codigoCliente) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarNegociosPropietario(codigoCliente)));
    }

    @PutMapping("/calificar-negocio/{codigoNegocio}/{calificar}")
    public ResponseEntity<MensajeDTO<String>> calificarNegocio(@PathVariable String codigoNegocio,
                                                               @PathVariable ValorCalificar calificar) throws Exception {

        negocioServicio.calificarNegocio(codigoNegocio, calificar);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Has calificado este negocio"));
    }

    @PutMapping("/calificacion-negocio/{codigo}")
    public ResponseEntity<MensajeDTO<Integer>> calcularPromedioCalificaficaciones(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.calcularPromedioCalificaficaciones(codigo)));
    }

    /*@GetMapping("/buscar-nombre/{nombreNegocio}")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> buscarNegocioPorNombre(@PathVariable String nombreNegocio) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.buscarNegocioPorNombre(nombreNegocio)));
    }*/

    @GetMapping("/lista-negocios-abiertos/{tipo}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listarNegociosAbiertosPorTipoSegunHora(@PathVariable
                                                                                                   TipoNegocio tipo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarNegociosAbiertosPorTipoSegunHora(tipo)));
    }

    @GetMapping("/obtener-favorito/{codigoNegocio}/{codigoCliente}")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> obtenerFavorito(@PathVariable String codigoNegocio,
                                                                         @PathVariable String codigoCliente) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.obtenerFavorito(codigoNegocio, codigoCliente)));
    }

    @GetMapping("/esta-disponible/{codigo}")
    public ResponseEntity<MensajeDTO<String>> determinarDisponibilidadNegocio(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.determinarDisponibilidadNegocio(codigo)));
    }

    @DeleteMapping("/eliminar-favorito/{codigoNegocio}/{codigoCliente}")
    public ResponseEntity<MensajeDTO<String>> eliminarFavorito(@PathVariable String codigoNegocio,
                                                               @PathVariable String codigoCliente) throws Exception {
        negocioServicio.eliminarNegocioFavorito(codigoNegocio, codigoCliente);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,
                "El negocio fue eliminado de su lista de favoritos con éxito"));
    }

    @GetMapping("/listar-favoritos-cliente/{codigoCliente}")
    public ResponseEntity<MensajeDTO<Set<ItemNegocioDTO>>> listarFavoritos(@PathVariable String codigoCliente) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarFavoritos(codigoCliente)));
    }

    @GetMapping("/listar-revisiones/{codigoNegocio}")
    public ResponseEntity<MensajeDTO<List<ItemRevisionDTO>>> listarRevisiones(@PathVariable String codigoNegocio) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarRevisiones(codigoNegocio)));
    }

    @PutMapping("/guardar-favorito/{codigoNegocio}/{codigoCliente}")
    public ResponseEntity<MensajeDTO<String>> guardarFavorito(@PathVariable String codigoNegocio,
                                                              @PathVariable String codigoCliente) throws Exception {
        negocioServicio.guardarNegocioFavorito(codigoNegocio, codigoCliente);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Has guardado este negocio entre tus favoritos"));
    }

    @GetMapping("/listar-recomendados-cliente/{codigoNegocio}")
    public ResponseEntity<MensajeDTO<Set<ItemNegocioDTO>>> listarRecomendadosCliente(@PathVariable String codigoNegocio) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarRecomendadosCliente(codigoNegocio)));
    }

    @GetMapping("/listar-recomendados")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listaNegociosRecomendadosPorClientes() throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listaNegociosRecomendadosPorClientes()));
    }

    @GetMapping("/obtener-revision")
    public ResponseEntity<MensajeDTO<DetalleRevisionDTO>> obtenerRevision(@Valid
                                                                          ItemRevisionDTO item) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.obtenerRevision(item)));
    }

    @GetMapping("/obtener-recomendado/{codigoNegocio}/{codigoCliente}")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> obtenerRecomendado(@PathVariable String codigoNegocio,
                                                                            @PathVariable String codigoCliente) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.obtenerRecomendado(codigoNegocio,codigoCliente)));
    }

    @DeleteMapping("/eliminar-recomendado/{codigoNegocio}/{codigoCliente}")
    public ResponseEntity<MensajeDTO<String>> eliminarRecomendado(@PathVariable String codigoNegocio,
                                                                  @PathVariable String codigoCliente) throws Exception {
        negocioServicio.eliminarNegocioRecomendado(codigoNegocio, codigoCliente);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,
                "El negocio fue eliminado de su lista de recomendados con éxito"));
    }

    @PutMapping("/guardar-recomendado/{codigoNegocio}/{codigoCliente}")
    public ResponseEntity<MensajeDTO<String>> guardarRecomendado(@PathVariable String codigoNegocio,
                                                                 @PathVariable String codigoCliente) throws Exception {
        negocioServicio.guardarRecomendado(codigoNegocio, codigoCliente);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Has recomendado este negocio"));
    }

    @GetMapping("/listar-tipos-negocio")
    public ResponseEntity<MensajeDTO<List<String>>> listarTiposNegocio() throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarTiposNegocio()));
    }


}
