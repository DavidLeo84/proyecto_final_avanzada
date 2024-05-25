package co.edu.uniquindio.proyecto.controller;


import co.edu.uniquindio.proyecto.dtos.ItemNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.MensajeDTO;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/publico")
@RequiredArgsConstructor
public class PublicoController {

    private final ClienteServicioImpl clienteServicio;
    private final NegocioServicioImpl negocioServicio;

    @GetMapping("/listar-tipos-negocio")
    public ResponseEntity<MensajeDTO<List<String>>> listarTiposNegocio() throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarTiposNegocio()));
    }

    @GetMapping("/listar-ciudades")
    public ResponseEntity<MensajeDTO<List<String>>> listarCiudades() throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, clienteServicio.listarCiudades()));
    }

    @GetMapping("/listar-negocios-palabra/{texto}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> listarNegociosPorPalabraComun(@PathVariable String texto) throws Exception {

        return ResponseEntity.ok().body(new MensajeDTO<>(false, negocioServicio.listarNegociosPorPalabraComun(texto)));
    }
}
