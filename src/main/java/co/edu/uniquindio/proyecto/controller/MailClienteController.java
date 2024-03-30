package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dtos.EmailDTO;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.interfaces.IEmailServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cliente")
public class MailClienteController {

    @Autowired
    private IEmailServicio emailServicio;
    @Autowired
    private ClienteServicioImpl clienteServicio;

    @PostMapping("/enviarMensaje")
    public ResponseEntity<Map> recibirPeticionEmail(@Valid @RequestBody EmailDTO emailDTO) {

        System.out.println("Mensaje recibido " + emailDTO.toString());

        emailServicio.enviarEmail(emailDTO.destinatario(), emailDTO.asunto(), emailDTO.mensaje());
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("estado", "email enviado");

        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/recopass")
    public ResponseEntity<Map> enviarEmailRecuperacion(@Valid @RequestBody String destinatario) throws Exception {

        clienteServicio.enviarLinkRecuperacion(destinatario);
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("estado", "email enviado");

        return ResponseEntity.ok(respuesta);
    }
}
