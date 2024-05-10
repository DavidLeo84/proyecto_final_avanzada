package co.edu.uniquindio.proyecto.controller;


import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.servicios.ModeradorServicioImpl;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moderadores")
@SecurityRequirement(name = "bearerAuth")
public class ModeradorController {

    private final ModeradorServicioImpl moderadorServicio;
    private final NegocioServicioImpl negocioServicio;

    @GetMapping("/filtrar-estado/{estado}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> filtrarPorEstado(@PathVariable EstadoNegocio estado) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.filtrarPorEstado(estado)));
    }

    @GetMapping("/obtener-revision")
    public ResponseEntity<MensajeDTO<DetalleRevisionDTO>> obtenerRevision(ItemRevisionDTO item) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.obtenerRevision(item)));
    }

    @GetMapping("/listar-revisiones/{codigo}")
    public ResponseEntity<MensajeDTO<List<ItemRevisionDTO>>> listarRevisiones(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarRevisiones(codigo)));
    }

    @GetMapping("/buscar-nombre/{codigo}")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> buscarNegocioPorNombre(@PathVariable String codigo) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.buscarNegocioPorNombre(codigo)));
    }

    @PutMapping("/borrar-cuenta/{codigo}")
    public ResponseEntity<MensajeDTO<String>> eliminarCuenta(@PathVariable String codigo) throws Exception {

        moderadorServicio.eliminarCuenta(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Se ha eliminado con éxito la cuenta"));
    }

    @PostMapping("/revisar-negocio")
    public ResponseEntity<MensajeDTO<String>> revisarNegocio(@Valid @RequestBody HistorialRevisionDTO revisionDTO ) throws Exception {

        moderadorServicio.revisarNegocio(revisionDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Se ha realizado la revisión con éxito"));
    }

    @GetMapping("/obtener-aprobado")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> obtenerNegocioAprobado(ItemNegocioDTO negocioDTO) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, moderadorServicio.obtenerNegocioAprobado(negocioDTO)));
    }

    @GetMapping("/obtener-pendiente")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> obtenerNegocioPendiente(ItemNegocioDTO negocioDTO) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, moderadorServicio.obtenerNegocioPendiente(negocioDTO)));
    }

    @GetMapping("/obtener-rechazado")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> obtenerNegocioRechazado(ItemNegocioDTO negocioDTO) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, moderadorServicio.obtenerNegocioRechazado(negocioDTO)));
    }

    @GetMapping("/obtener-eliminado")
    public ResponseEntity<MensajeDTO<DetalleNegocioDTO>> obtenerNegocioEliminado(ItemNegocioDTO negocioDTO) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, moderadorServicio.obtenerNegocioEliminado(negocioDTO)));
    }

    @GetMapping("/listar-aprobados")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listarNegociosAprobados() throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, moderadorServicio.listarNegociosAprobados()));
    }

    @GetMapping("/listar-pendientes")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listarNegociosPendientes() throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, moderadorServicio.listarNegociosPendientes()));
    }

    @GetMapping("/listar-rechazados")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listarNegociosRechazados() throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, moderadorServicio.listarNegociosRechazados()));
    }

    @GetMapping("/listar-eliminados")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listarNegociosEliminados() throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, moderadorServicio.listarNegociosEliminados()));
    }










}
