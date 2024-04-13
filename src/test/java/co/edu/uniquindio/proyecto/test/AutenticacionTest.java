package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dtos.LoginDTO;
import co.edu.uniquindio.proyecto.dtos.TokenDTO;
import co.edu.uniquindio.proyecto.servicios.interfaces.IAutenticacionServicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AutenticacionTest {

    @Autowired
    private IAutenticacionServicio autenticacionServicio;

    @DisplayName("Test para validar e iniciar sesion con los datos validos de un cliente")
    @Test
    public void iniciarSesionClienteTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        LoginDTO loginDTO = new LoginDTO("ronnie@gmail.com", "123456");

        // When - Acción o el comportamiento que se va a probar
        TokenDTO tokenDTO = autenticacionServicio.iniciarSesionCliente(loginDTO);

        //Then - Verificar la salida
        System.out.println("tokenDTO.toString() = " + tokenDTO.toString());
        assertThat(tokenDTO).isNotNull();
    }

    @DisplayName("Test para validar e iniciar sesion con los datos validos de un cliente")
    @Test
    public void iniciarSesionModeradorTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        LoginDTO loginDTO = new LoginDTO("mode1@correo.com", "1234");

        // When - Acción o el comportamiento que se va a probar
        TokenDTO tokenDTO = autenticacionServicio.iniciarSesionModerador(loginDTO);

        //Then - Verificar la salida
        System.out.println("tokenDTO.toString() = " + tokenDTO.toString());
        assertThat(tokenDTO).isNotNull();
    }
}
