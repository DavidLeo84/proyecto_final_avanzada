package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dtos.LoginDTO;
import co.edu.uniquindio.proyecto.dtos.TokenDTO;
import co.edu.uniquindio.proyecto.servicios.AutenticacionServicioImpl;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;

import co.edu.uniquindio.proyecto.servicios.CuentaServicioImpl;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AutenticacionTest {

    @Autowired
    private AutenticacionServicioImpl autenticacionServicio;
    @Autowired
    private ClienteServicioImpl clienteServicio;
    @Autowired
    private CuentaServicioImpl cuentaServicio;

    @DisplayName("Test para validar e iniciar sesion con los datos validos de un cliente")
    @Test
    public void iniciarSesionTest() throws Exception {

        // Given - Dado o condicion previa o configuración
          LoginDTO loginDTO = new LoginDTO("marycano@gmail.com", "12345678");
//        LoginDTO loginDTO = new LoginDTO("mode1@correo.com", "1234");

        // When - Acción o el comportamiento que se va a probar
        TokenDTO tokenDTO = cuentaServicio.iniciarSesion(loginDTO);

        //Then - Verificar la salida
        System.out.println("tokenDTO.toString() = " + tokenDTO.toString());
        assertThat(tokenDTO).isNotNull();
    }

    @DisplayName("Test para validar el mensaje de error por password al iniciar sesion con los datos invalidos de un cliente")
    @Test
    public void iniciarSesionErrorPasswordTest() throws Exception {

        // Given - Dado o condicion previa o configuración
           LoginDTO loginDTO = new LoginDTO("marycano@gmail.com", "87654321");

        //When - Then - Verificar la salida
        assertThrows(NoSuchElementException.class, () -> cuentaServicio.iniciarSesion(loginDTO));
    }

    @DisplayName("Test para validar el mensaje de error por email al iniciar sesion con los datos invalidos de un cliente")
    @Test
    public void iniciarSesionErrorEmailTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        LoginDTO loginDTO = new LoginDTO("mariacano@gmail.com", "12345678");

        //When - Then - Verificar la salida
        assertThrows(Exception.class, () -> cuentaServicio.iniciarSesion(loginDTO));
    }

    @DisplayName("Test para enviar enlace por email para recuperar password")
    @Test
    public void enviarLinkRecuperacionTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        TokenDTO tokenDTO = cuentaServicio.enviarLinkRecuperacion("marycano@gmail.com");

        //Then - Verificar la salida
        System.out.println("tokenDTO.toString() = " + tokenDTO.toString());
        assertThat(tokenDTO).isNotNull();

    }

    @DisplayName("Test para verificar el mensaje de error al enviar un e-mail no registrado para recuperar password")
    @Test
    public void enviarLinkRecuperacionErrorEmailTest() throws Exception {

        //When - Then - Verificar la salida
        assertThrows(ResourceNotFoundException.class, () -> cuentaServicio.enviarLinkRecuperacion("maricano@gmail.com"));
    }
}
