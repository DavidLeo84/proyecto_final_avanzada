package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dtos.CambioPasswordDTO;
import co.edu.uniquindio.proyecto.dtos.DetalleNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.HistorialRevisionDTO;
import co.edu.uniquindio.proyecto.dtos.ItemNegocioDTO;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.ModeradorServicioImpl;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionModerador;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionNegocio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ModeradorTest {

    @Autowired
    private ValidacionCliente validacionCliente;
    @Autowired
    private ValidacionModerador validacionModerador;
    @Autowired
    private ValidacionNegocio validacionNegocio;
    @Autowired
    ModeradorRepo moderadorRepo;
    @Autowired
    private ClienteServicioImpl clienteServicio;
    @Autowired
    private ModeradorServicioImpl moderadorServicio;
    @Autowired
    private NegocioServicioImpl negocioServicio;
    private List<TipoNegocio> tipoNegocios = new ArrayList<>();

    @BeforeEach
    void setup() {

        tipoNegocios.add(TipoNegocio.COMIDAS_RAPIDAS);
        tipoNegocios.add(TipoNegocio.BAR);
    }

    @DisplayName("Test para eliminar la cuenta de un moderador")
    @Test
    public void eliminarCuentaTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        Moderador moderador = validacionModerador.buscarModerador("6618bece184b4b36653f9ada");

        /*When - Acción o el comportamiento que se va a probar*/
        moderadorServicio.eliminarCuenta(moderador.getCodigo());

        /*Then - Verificar la salida*/
        assertThrows(ResourceNotFoundException.class, () -> validacionModerador.buscarModerador("3"));
    }

    @DisplayName("Test para registrar una revision a un negocio creado")
    @Test
    public void registrarHistorialRevisionTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        HistorialRevisionDTO revisionDTO = new HistorialRevisionDTO(
                "Su negocio cumple con las normas de la aplicación",
                EstadoNegocio.APROBADO,
                "661ca68e157f5040899baeeb",
                "663d54317a25b95b79e76c96"
        );
        /*When - Acción o el comportamiento que se va a probar*/
        moderadorServicio.revisarNegocio(revisionDTO);

        /*Then - Verificar la salida*/
        Negocio negocio = validacionNegocio.validarNegocioAprobado("663d54317a25b95b79e76c96");
        System.out.println("negocio = " + negocio);
        assertThat(negocio.getHistorialRevisiones().size()).isGreaterThan(1);
    }

    @DisplayName("Test para buscar y mostrar un negocio que este en estado pendiente para revisión")
    @Test
    public void obtenerNegocioAprobadoTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        ItemNegocioDTO negocioDTO = new ItemNegocioDTO(
                "661aacb404561d72bdbf16f2",
                "La Perrada de Ronnie",
                tipoNegocios
        );
        /*When - Acción o el comportamiento que se va a probar*/
        DetalleNegocioDTO negocio = moderadorServicio.obtenerNegocioAprobado(negocioDTO);

        /*Then - Verificar la salida*/
        System.out.println("negocio = " + negocio);
        assertThat(negocioDTO).isNotNull();
    }

    @DisplayName("Test para buscar y mostrar un negocio que este en estado pendiente para revisión")
    @Test
    public void obtenerNegocioPendienteTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        ItemNegocioDTO negocioDTO = new ItemNegocioDTO(
                "6608abf0548f03646c38dbd8",
                "La Primera Perrada de Ronnie",
                tipoNegocios
        );
        /*When - Acción o el comportamiento que se va a probar*/
        DetalleNegocioDTO negocio = moderadorServicio.obtenerNegocioPendiente(negocioDTO);

        /*Then - Verificar la salida*/
        System.out.println("negocio = " + negocio);
        assertThat(negocio).isNotNull();
    }

    @DisplayName("Test para buscar y mostrar un negocio que este en estado pendiente para revisión")
    @Test
    public void obtenerNegocioRechazadoTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        ItemNegocioDTO negocioDTO = new ItemNegocioDTO(
                "660ee420767f881e7797e463",
                "La Cuarta Perrada de Ronnie",
                tipoNegocios
        );

        /*When - Acción o el comportamiento que se va a probar*/
        DetalleNegocioDTO negocio = moderadorServicio.obtenerNegocioRechazado(negocioDTO);

        /*Then - Verificar la salida*/
        System.out.println("negocio = " + negocio);
        assertThat(negocio).isNotNull();
    }

    @DisplayName("Test para buscar y mostrar un negocio que este en estado pendiente para revisión")
    @Test
    public void obtenerNegocioEliminadoTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        ItemNegocioDTO negocioDTO = new ItemNegocioDTO(
                "660ee420767f881e7797e463",
                "La Cuarta Perrada de Ronnie",
                tipoNegocios
        );
        /*When - Acción o el comportamiento que se va a probar*/
        DetalleNegocioDTO negocio = moderadorServicio.obtenerNegocioEliminado(negocioDTO);

        /*Then - Verificar la salida*/
        System.out.println("negocio = " + negocio);
        assertThat(negocio).isNotNull();
    }

    @DisplayName("Test crear una lista de los negocios que tienen estado negocio como aprobado")
    @Test
    public void listarNegociosAprobadosTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        List<ItemNegocioDTO> lista = moderadorServicio.listarNegociosAprobados();

        /*Then - Verificar la salida*/
        Assertions.assertEquals(5, lista.size());
    }

    @DisplayName("Test crear una lista de los negocios que tienen estado negocio como pendiente")
    @Test
    public void listarNegociosPendientesTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        List<ItemNegocioDTO> lista = moderadorServicio.listarNegociosPendientes();

        /*Then - Verificar la salida*/
        Assertions.assertEquals(1, lista.size());
    }

    @DisplayName("Test crear una lista de los negocios que tienen estado negocio como rechazados")
    @Test
    public void listarNegociosRechazadosTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        List<ItemNegocioDTO> lista = moderadorServicio.listarNegociosRechazados();

        /*Then - Verificar la salida*/
        Assertions.assertEquals(1, lista.size());
    }

    @DisplayName("Test crear una lista de los negocios que tienen estado negocio como eliminados")
    @Test
    public void listarNegociosEliminadosTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        List<ItemNegocioDTO> lista = moderadorServicio.listarNegociosEliminados();

        /*Then - Verificar la salida*/
        Assertions.assertEquals(1, lista.size());
    }

    @DisplayName("Test para cambiar el password de un moderador")
    @Test
    public void cambiarPasswordTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        CambioPasswordDTO passwordDTO = new CambioPasswordDTO(
                "1234",
                "661ca68e157f5040899baeeb"
        );

        // When - Acción o el comportamiento que se va a probar
        String cambio = moderadorServicio.cambiarPassword(passwordDTO);

        //Then - Verificar la salida
        assertThat(cambio).isEqualTo("El password fue cambiado con éxito");
    }
}
