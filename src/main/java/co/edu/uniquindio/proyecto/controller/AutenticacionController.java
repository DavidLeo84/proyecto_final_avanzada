package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.servicios.AutenticacionServicioImpl;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.CuentaServicioImpl;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import co.edu.uniquindio.proyecto.servicios.interfaces.ICuentaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacionController{

    private final AutenticacionServicioImpl autenticacionServicio;
    private final ClienteServicioImpl clienteServicio;
    private final CuentaServicioImpl cuentaServicio;



    @PostMapping("/login")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesionCliente(@Valid @RequestBody
                                                                     LoginDTO loginDTO) throws Exception {
        TokenDTO tokenDTO = cuentaServicio.iniciarSesion(loginDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, tokenDTO));
    }

   /* @PostMapping("/login-cliente")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesionCliente(@Valid @RequestBody
                                                                     LoginDTO loginDTO) throws Exception {
        TokenDTO tokenDTO = autenticacionServicio.iniciarSesionCliente(loginDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, tokenDTO));
    }*/

   /* @PostMapping("/login-moderador")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesionModerador(@Valid @RequestBody
                                                                     LoginDTO loginDTO) throws Exception {
        TokenDTO tokenDTO = autenticacionServicio.iniciarSesionModerador(loginDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, tokenDTO));
    }*/

    @PostMapping("/registrar-cliente")
    public ResponseEntity<MensajeDTO<String>> registrarCliente(@Valid @RequestBody
                                                               RegistroClienteDTO registroClienteDTO) throws Exception {
        clienteServicio.registrarse(registroClienteDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El cliente fue registrado correctamente"));
    }

    @GetMapping("/recopass/{email}")
    public ResponseEntity<MensajeDTO<TokenDTO>> enviarLinkRecuperacion(@Valid @PathVariable String email) throws Exception {

       TokenDTO token = cuentaServicio.enviarLinkRecuperacion(email);
       return ResponseEntity.ok().body(new MensajeDTO<>(false, token));
    }


}
