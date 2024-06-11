package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dtos.CambioPasswordDTO;
import co.edu.uniquindio.proyecto.dtos.DetalleNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.HistorialRevisionDTO;
import co.edu.uniquindio.proyecto.dtos.ItemNegocioDTO;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.modelo.Ubicacion;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
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
import java.util.NoSuchElementException;
import java.util.Optional;

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
    private NegocioRepo negocioRepo;
    @Autowired
    private ClienteServicioImpl clienteServicio;
    @Autowired
    private ModeradorServicioImpl moderadorServicio;
    @Autowired
    private NegocioServicioImpl negocioServicio;
    private List<String> tipoNegocios = new ArrayList<>();

    @BeforeEach
    void setup() {

        tipoNegocios.add(TipoNegocio.COMIDAS_RAPIDAS.name());
        tipoNegocios.add(TipoNegocio.BAR.name());
    }

    @DisplayName("Test para eliminar la cuenta de un moderador")
    @Test
    public void eliminarCuentaTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        Moderador moderador = validacionModerador.buscarModerador("661ca68e157f5040899baeec");

        /*When - Acción o el comportamiento que se va a probar*/
        moderadorServicio.eliminarCuenta(moderador.getCodigo());

        /*Then - Verificar la salida*/
        assertThrows(ResourceNotFoundException.class, () -> validacionModerador.buscarModerador("661ca68e157f5040899baeec"));
    }

    @DisplayName("Test para validar error de moderador inexistente al eliminar la cuenta")
    @Test
    public void eliminarCuentaErrorModeradorTest() throws Exception {

        /*Given - When -Then - Verificar la salida*/
        assertThrows(ResourceNotFoundException.class, () ->
                validacionModerador.buscarModerador("661ca68e157f5040899baeec"));
    }

    @DisplayName("Test para registrar una revision a un negocio creado")
    @Test
    public void registrarHistorialRevisionTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        HistorialRevisionDTO revisionDTO = new HistorialRevisionDTO(
                "Su negocio NO cumple con las normas de la aplicación",
                EstadoNegocio.RECHAZADO,
                "661ca68e157f5040899baeeb",
                "665b85be1c057873df6aa4ba"
        );
        /*When - Acción o el comportamiento que se va a probar*/
        moderadorServicio.revisarNegocio(revisionDTO);

        /*Then - Verificar la salida*/
        Optional<Negocio> negocio = negocioRepo.findByCodigoNegocio("665b85be1c057873df6aa4ba");
        System.out.println("Revisado = " + negocio.get());
        assertThat(negocio.get().getHistorialRevisiones().size()).isGreaterThan(1);
    }

    @DisplayName("Test para validar el error al registrar una revision de un negocio creado con un local ya existente")
    @Test
    public void registrarHistorialRevisionErrorLocalTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        HistorialRevisionDTO revisionDTO = new HistorialRevisionDTO(
                "Su negocio cumple con las normas de la aplicación",
                EstadoNegocio.APROBADO,
                "661ca68e157f5040899baeeb",
                "665b817468519a70ab47295e"
        );
        /*When - Then - Verificar la salida*/
        assertThrows(ResourceNotFoundException.class, () -> moderadorServicio.revisarNegocio(revisionDTO));
    }

    @DisplayName("Test para buscar y mostrar un negocio que este en estado pendiente para revisión")
    @Test
    public void obtenerNegocioAprobadoTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        Negocio negocio = validacionNegocio.validarNegocioAprobado("661dd5c7dd12ce75301659ef");
        ItemNegocioDTO negocioDTO = new ItemNegocioDTO(
                negocio.getCodigoNegocio(),
                negocio.getNombre(),
                negocio.getTipoNegocios(),
                negocio.getUbicacion(),
                negocio.getImagenes()
        );
        /*When - Acción o el comportamiento que se va a probar*/
        DetalleNegocioDTO detealleNegocio = moderadorServicio.obtenerNegocioAprobado(negocioDTO);

        /*Then - Verificar la salida*/
        System.out.println("negocio = " + detealleNegocio);
        assertThat(detealleNegocio).isNotNull();
    }

    @DisplayName("Test para buscar y mostrar un negocio que este en estado pendiente para revisión")
    @Test
    public void obtenerNegocioPendienteTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        Negocio negocio = validacionNegocio.validarNegocioPendiente("665b817468519a70ab47295e");
        ItemNegocioDTO item = new ItemNegocioDTO(
                negocio.getCodigoNegocio(),
                negocio.getNombre(),
                negocio.getTipoNegocios(),
                negocio.getUbicacion(),
                negocio.getImagenes());

        /*When - Acción o el comportamiento que se va a probar*/
        DetalleNegocioDTO detalleNegocio = moderadorServicio.obtenerNegocioPendiente(item);

        /*Then - Verificar la salida*/
        System.out.println("detalleNegocio = " + detalleNegocio);
        assertThat(detalleNegocio).isNotNull();
    }

    @DisplayName("Test para validar el error de un negocio que tiene otro estado diferente a pendiente")
    @Test
    public void obtenerNegocioPendienteErrorNegocioTest() throws Exception {

        /*Give - When - Then - Verificar la salida*/
        assertThrows(Exception.class, () ->
                validacionNegocio.validarNegocioPendiente("661dd6810534cd610ad1beb6"));
    }

    @DisplayName("Test para buscar y mostrar un negocio que este en estado pendiente para revisión")
    @Test
    public void obtenerNegocioRechazadoTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        Negocio negocio = validacionNegocio.validarNegocioRechazado("665b817468519a70ab47295e");
        ItemNegocioDTO negocioDTO = new ItemNegocioDTO(
                negocio.getCodigoNegocio(),
                negocio.getNombre(),
                negocio.getTipoNegocios(),
                negocio.getUbicacion(),
                negocio.getImagenes()
        );

        /*When - Acción o el comportamiento que se va a probar*/
        DetalleNegocioDTO detalleNegocio = moderadorServicio.obtenerNegocioRechazado(negocioDTO);

        /*Then - Verificar la salida*/
        System.out.println("negocio = " + detalleNegocio);
        assertThat(detalleNegocio).isNotNull();
    }

    @DisplayName("Test para buscar y mostrar un negocio que este en estado pendiente para revisión")
    @Test
    public void obtenerNegocioEliminadoTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        Negocio negocio = validacionNegocio.validarNegocioEliminado("6625d797f55be6771e1ac152");
        ItemNegocioDTO negocioDTO = new ItemNegocioDTO(
                negocio.getCodigoNegocio(),
                negocio.getNombre(),
                negocio.getTipoNegocios(),
                negocio.getUbicacion(),
                negocio.getImagenes()
        );
        /*When - Acción o el comportamiento que se va a probar*/
        DetalleNegocioDTO detalleNegocio = moderadorServicio.obtenerNegocioEliminado(negocioDTO);

        /*Then - Verificar la salida*/
        System.out.println("negocio = " + detalleNegocio);
        assertThat(detalleNegocio).isNotNull();
    }

    @DisplayName("Test crear una lista de los negocios que tienen estado negocio como aprobado")
    @Test
    public void listarNegociosAprobadosTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        List<ItemNegocioDTO> lista = moderadorServicio.listarNegociosAprobados();

        /*Then - Verificar la salida*/
        Assertions.assertEquals(7 , lista.size());
    }

    @DisplayName("Test crear una lista de los negocios que tienen estado negocio como pendiente")
    @Test
    public void listarNegociosPendientesTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        List<ItemNegocioDTO> lista = moderadorServicio.listarNegociosPendientes();

        /*Then - Verificar la salida*/
        Assertions.assertEquals(3, lista.size());
    }

    @DisplayName("Test validar el error de una lista vacia al listar los negocios que tienen estado como pendiente")
    @Test
    public void listarNegociosPendientesErrorListaVaciaTest() throws Exception {

        /*When - Then - Verificar la salida*/
        assertThrows(ResourceNotFoundException.class, () -> moderadorServicio.listarNegociosPendientes());
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
                "87654321",
                "12345678",
                "661ca68e157f5040899baeeb"
        );

        // When - Acción o el comportamiento que se va a probar
        String cambio = moderadorServicio.cambiarPassword(passwordDTO);

        //Then - Verificar la salida
        assertThat(cambio).isEqualTo("El password fue cambiado con éxito");
    }

    @DisplayName("Test para validar error de password errada cambiar el password de un moderador")
    @Test
    public void cambiarPasswordErrorPasswordErradaTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        CambioPasswordDTO passwordDTO = new CambioPasswordDTO(
                "dsshfsad",
                "87654321",
                "661ca68e157f5040899baeeb"
        );

        //When - Then - Verificar la salida
        assertThrows(NoSuchElementException.class, () -> moderadorServicio.cambiarPassword(passwordDTO));
    }
}
