package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.modelo.documentos.Comentario;
import co.edu.uniquindio.proyecto.dtos.RegistroComentarioDTO;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.ComentarioServicioImpl;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionNegocio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ComentarioTest {

    @Autowired
    private NegocioRepo negocioRepo;
    @Autowired
    private NegocioServicioImpl negocioServicio;
    @Autowired
    private ValidacionNegocio validacionNegocio;
    @Autowired
    private ClienteServicioImpl clienteServicio;
    @Autowired
    private ValidacionCliente validacionCliente;
    @Autowired
    private ComentarioServicioImpl comentarioServicio;

    @DisplayName("Test para guardar el registrod de un comentario")
    @Test
    public void registrarComentarioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        RegistroComentarioDTO comentarioDTO = new RegistroComentarioDTO(
                "660842f2e1f50b64a6376e3c",
                "6608438bfd6d342c8005bdc8",
                "este es el mensaje",
                "esta es la respuesta",
                LocalDateTime.now()
                );

        // When - Acción o el comportamiento que se va a probar
        Comentario nuevo = comentarioServicio.crearComentario(comentarioDTO);

        //Then - Verificar la salida
        assertThat(nuevo).isNotNull();
    }

}
