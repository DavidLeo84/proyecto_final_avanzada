package co.edu.uniquindio.proyecto.controller;


import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.servicios.ComentarioServicioImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comentarios")
@SecurityRequirement(name = "bearerAuth")
public class ComentarioController {

    private final ComentarioServicioImpl comentarioServicio;

    @PostMapping("/crear-comentario")
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
