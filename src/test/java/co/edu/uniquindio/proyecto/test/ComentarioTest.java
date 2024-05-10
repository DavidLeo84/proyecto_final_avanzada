package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dtos.DetalleComentarioDTO;
import co.edu.uniquindio.proyecto.dtos.ItemComentarioDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroRespuestaComentarioDTO;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Comentario;
import co.edu.uniquindio.proyecto.dtos.RegistroComentarioDTO;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.ComentarioServicioImpl;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import co.edu.uniquindio.proyecto.servicios.excepciones.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ComentarioTest {

    @Autowired
    private NegocioRepo negocioRepo;
    @Autowired
    private NegocioServicioImpl negocioServicio;
    @Autowired
    private ValidacionNegocio validacionNegocio;
    @Autowired
    private ValidacionComentario validacionComentario;
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
                "661c4a4289852b27687d80a7",
                "663d54317a25b95b79e76c96",
                "este es un comentario",
                LocalDateTime.now()
        );

        // When - Acción o el comportamiento que se va a probar
        Comentario nuevo = comentarioServicio.crearComentario(comentarioDTO);
        //Then - Verificar la salida
        assertThat(nuevo).isNotNull();
    }

    @DisplayName("Test para responder a un comentario")
    @Test
    public void responderComentarioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        Comentario comentario = validacionComentario.validarComentario("663d8552c2315b0e45df1e31");

        RegistroRespuestaComentarioDTO respuestaDTO = new RegistroRespuestaComentarioDTO(
                comentario.getCodigo(),
                comentario.getCodigoCliente(),
                comentario.getCodigoNegocio(),
                comentario.getMensaje(),
                comentario.getFechaMensaje(),
                "Esta es la respuesta al comentario",
                LocalDateTime.now()
                );

        // When - Acción o el comportamiento que se va a probar
        Comentario respuesta = comentarioServicio.responderComentario(respuestaDTO);

        //Then - Verificar la salida
        assertThat(respuesta).isNotNull();
    }

    @DisplayName("Test para listar los comentarios de un negocio")
    @Test
    public void listarComentariosNegocio() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        List<ItemComentarioDTO> listaComentarios = comentarioServicio.listarComentariosNegocio("661dd3c07afe983885b1783c");

        //Then - Verificar la salida
        Assertions.assertEquals(4, listaComentarios.size());
    }


    @DisplayName("Test para obtener un comentario de un negocio")
    @Test
    public void obtenerComentario() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        DetalleComentarioDTO comentarioDTO = comentarioServicio.obtenerComentarioNegocio("663d8552c2315b0e45df1e31");

        //Then - Verificar la salida
        System.out.println("comentarioDTO = " + comentarioDTO.toString());
        assertThat(comentarioDTO).isNotNull();

    }



    @DisplayName("Test para realizar una aprobacion (me gusta)  a un comentario")
    @Test
    public void aprobarComentarioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        //validacionComentario.validarListaAprobaciones("6608622d7a6bf86424f727d3");

        // When - Acción o el comportamiento que se va a probar
        comentarioServicio.aprobarComentario("663d8552c2315b0e45df1e31", "661c4b0ea2ece971f83f5a7b");

        //Then - Verificar la salida
        Cliente cliente = validacionCliente.buscarCliente("661c4b0ea2ece971f83f5a7b");
        Comentario comentario = validacionComentario.validarComentario("663d8552c2315b0e45df1e31");

        Assertions.assertEquals(1, cliente.getAprobacionesComentarios().size()); //cantidad de codigos de comentarios que ha aprobado el cliente
        Assertions.assertEquals(2, comentario.getMeGusta().size()); // cantidad de megusta que tiene el comentario
    }

    /*@DisplayName("Test para calcular la cantidad de meGusta que le han dado a un comentario")
    @Test
    public void calcularCantidadMegustaTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        int cantidad = comentarioServicio.calcularCantidadMegusta("660eb8c49bcd74720e2df999");

        //Then - Verificar la salida
        Comentario comentario = validacionComentario.validarComentario("660eb8c49bcd74720e2df999");

        Assertions.assertEquals(3, comentario.getMeGusta().size());
    }*/

}
