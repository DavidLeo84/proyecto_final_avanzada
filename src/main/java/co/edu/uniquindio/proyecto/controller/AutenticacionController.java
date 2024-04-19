package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.servicios.AutenticacionServicioImpl;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacionController {

    private final AutenticacionServicioImpl autenticacionServicio;
    private final ClienteServicioImpl clienteServicio;

    @PostMapping("/login-cliente")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesionCliente(@Valid @RequestBody
                                                                     LoginDTO loginDTO) throws Exception {
        TokenDTO tokenDTO = autenticacionServicio.iniciarSesionCliente(loginDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, tokenDTO));
    }

    @PostMapping("/login-moderador")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesionModerador(@Valid @RequestBody
                                                                     LoginDTO loginDTO) throws Exception {
        TokenDTO tokenDTO = autenticacionServicio.iniciarSesionModerador(loginDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, tokenDTO));
    }

    @PostMapping("/registrar-cliente")
    public ResponseEntity<MensajeDTO<String>> registrarCliente(@Valid @RequestBody
                                                               RegistroClienteDTO registroClienteDTO) throws Exception {
        clienteServicio.registrarse(registroClienteDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El cliente fue registrado correctamente"));
    }

    @GetMapping("/recopass/{email}")
    public ResponseEntity<MensajeDTO<TokenDTO>> enviarLinkRecuperacion(@Valid @PathVariable String email) throws Exception {

       TokenDTO token = clienteServicio.enviarLinkRecuperacion(email);
       return ResponseEntity.ok().body(new MensajeDTO<>(false, token));
    }





}
