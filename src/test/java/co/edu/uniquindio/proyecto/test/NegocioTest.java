package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.*;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.Horario;
import co.edu.uniquindio.proyecto.modelo.Ubicacion;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionNegocio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@SpringBootTest
public class NegocioTest {

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

    private List<Horario> horarios = new ArrayList<>();
    private List<TipoNegocio> tipoNegocios = new ArrayList<>();
    private Set<String> telefonos = new HashSet<>();
    private Set<String> imagenes = new HashSet<>();


    @BeforeEach
    void setup() {

        telefonos.add("3102647656");
        telefonos.add("3018967657");

        imagenes.add("cloudinary_1.com");
        imagenes.add("cloudinary_2.com");

        tipoNegocios.add(TipoNegocio.COMIDAS_RAPIDAS);
        tipoNegocios.add(TipoNegocio.BAR);

        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime horaInicio = LocalTime.of(10, 00);
        String abierto = formatoHora.format(horaInicio);

        LocalTime horaFin = LocalTime.of(21, 00);
        String cierre = formatoHora.format(horaFin);

        //String dia = LocalDate.now().getDayOfWeek().name();

        horarios.add(new Horario(DiaSemana.MONDAY, abierto, cierre));

    }

    @DisplayName("Test para guardar o registrar un negocio")
    @Test
    public void registrarNegocioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        RegistroNegocioDTO negocioDTO = new RegistroNegocioDTO(
                "Ferreteria Guaduales",
                "661c4b6c03dc96547afaca75",
                 new Ubicacion(-23.49732, 25.49943),
                "herramientas...",
                tipoNegocios,
                horarios,
                telefonos,
                imagenes
        );
        // When - Acción o el comportamiento que se va a probar
        Negocio nuevo = negocioServicio.crearNegocio(negocioDTO);
        //Then - Verificar la salida
        assertThat(nuevo).isNotNull();
    }

    @DisplayName("Test para actualizar la descripcion de un negocio")
    @Test
    public void actualizarNegocioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        Negocio negocio = validacionNegocio.buscarNegocioRechazadoParaModificar("6625dbdd01c3b62a2ecf681c");
        ActualizarNegocioDTO negocioDTO = new ActualizarNegocioDTO(
                negocio.getCodigo(),
                negocio.getDescripcion(),
                new Ubicacion(-23.42432,25.43243),
                negocio.getHorarios(),
                negocio.getTelefonos(),
                negocio.getImagenes()

        );
        // When - Acción o el comportamiento que se va a probar
        negocioServicio.actualizarNegocio(negocioDTO);

        //Then - Verificar la salida
        Negocio buscado = validacionNegocio.validarNegocioPendiente("6625dbdd01c3b62a2ecf681c");
        assertThat(buscado.getUbicacion().getLatitud()).isEqualTo(234243243);
        assertThat(buscado.getUbicacion().getLongitud()).isEqualTo(234324324);
    }

    @DisplayName("Test para eliminar un negocio")
    @Test
    public void eliminarNegocioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        Negocio negocio = validacionNegocio.buscarNegocio("661dd3c07afe983885b1783c");

        // When - Acción o el comportamiento que se va a probar
        negocioServicio.eliminarNegocio(negocio.getCodigo());

        //Then - Verificar la salida
        assertThrows(ResourceNotFoundException.class, () -> validacionNegocio.buscarNegocio("661dd3c07afe983885b1783c"));
    }

    @DisplayName("Test para buscar un negocio")
    @Test
    public void ObtenerNegocioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        Negocio negocio = validacionNegocio.buscarNegocio("661dd3c07afe983885b1783c");

        // When - Acción o el comportamiento que se va a probar
        DetalleNegocioDTO negocioDTO = negocioServicio.obtenerNegocio("661dd3c07afe983885b1783c");

        //Then - Verificar la salida
        assertThat(negocioDTO).isNotNull();
        System.out.println("negocioDTO.toString() = " + negocioDTO.toString());
    }

    //Volver a probar
    @DisplayName("Test para mostrar una lista de negocios buscados por un estado en especifico")
    @Test
    public void filtrarPorEstadoTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        List<ItemNegocioDTO> lista = negocioServicio.filtrarPorEstado(EstadoNegocio.APROBADO);

        //Then - Verificar la salida
        Assertions.assertEquals(6, lista.size());
    }

    @DisplayName("Test para mostrar una lista de negocios buscados por el codigo de un cliente")
    @Test
    public void listarNegociosPropietarioTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        List<ItemNegocioDTO> lista = negocioServicio.listarNegociosPropietario("661c4b6c03dc96547afaca75");

        //Then - Verificar la salida
        Assertions.assertEquals(2, lista.size());
    }

    @DisplayName("Test para guardar en una lista los negocios que recomienda")
    @Test
    public void guardarRecomendadosTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        negocioServicio.guardarRecomendado("661dd3c07afe983885b1783c", "661c4b6c03dc96547afaca75");

        //Then - Verificar la salida
        Cliente cliente = validacionCliente.buscarCliente("661c4b6c03dc96547afaca75");
        Assertions.assertEquals(1, cliente.getRecomendados().size());
    }

    @DisplayName("Test para mostrar un negocio de la lista de recomendados del cliente")
    @Test
    public void obtenerRecomendadoTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        DetalleNegocioDTO negocioDTO = negocioServicio.obtenerRecomendado("661dd3c07afe983885b1783c","661c4b6c03dc96547afaca75");

        //Then - Verificar la salida
        System.out.println("negocioDTO = " + negocioDTO);
        assertThat(negocioDTO).isNotNull();
    }

    @DisplayName("Test para eliminar un negocio de la lista de recomendados de un cliente")
    @Test
    public void eliminarNegocioRecomendado() throws Exception {

        // Given - Dado o condicion previa o configuración

        // When - Acción o el comportamiento que se va a probar
        String eliminado = negocioServicio.eliminarNegocioRecomendado("661dd3c07afe983885b1783c","661c4b6c03dc96547afaca75");

        //Then - Verificar la salida
        Assertions.assertEquals(eliminado, "El negocio fue eliminado de su lista de recomendados con éxito");

    }

    @DisplayName("Test para mostrar una lista de negocios recomendados del cliente")
    @Test
    public void listarRecomendadosTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        Set<ItemNegocioDTO> lista = negocioServicio.listarRecomendadosCliente("661c4b6c03dc96547afaca75");

        //Then - Verificar la salida
        Assertions.assertEquals(1, lista.size());
    }

    @DisplayName("Test para listar los negocios mas recomendados por los clientes")
    @Test
    public void listaNegociosRecomendadosPorClientes() throws Exception{

        // Given - Dado o condicion previa o configuración

        // When - Acción o el comportamiento que se va a probar
        List<ItemNegocioDTO> masRecomendados = negocioServicio.listaNegociosRecomendadosPorClientes();

        //Then - Verificar la salida
        Assertions.assertEquals(1, masRecomendados.size());

    }

    @DisplayName("Test para buscar una revision del historial de revisiones de un negocio")
    @Test
    public void obtenerRevisionTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        ItemRevisionDTO item = new ItemRevisionDTO("661dd3c07afe983885b1783c", "2024/04/15 08:48:21.000 PM");
        
        // When - Acción o el comportamiento que se va a probar
        DetalleRevisionDTO revision = negocioServicio.obtenerRevision(item);

        //Then - Verificar la salida
        System.out.println("revision.toString() = " + revision.toString());
        assertThat(revision).isNotNull();
    }

    @DisplayName("Test para listar las revisiones que tenga un negocio")
    @Test
    public void listarRevisionesTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        List<ItemRevisionDTO> revisionDTO = negocioServicio.listarRevisiones("661dd3c07afe983885b1783c");

        //Then - Verificar la salida
        Assertions.assertEquals(2, revisionDTO.size());

    }

    @DisplayName("Test para guardar el codigo del negocio en la lista de favoritos del cliente")
    @Test
    public void guardarNegocioFavoritoTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        negocioServicio.guardarNegocioFavorito("661dd3c07afe983885b1783c", "661c4b6c03dc96547afaca75");

        //Then - Verificar la salida
        Cliente cliente = validacionCliente.buscarCliente("661c4b6c03dc96547afaca75");
        Negocio buscado = validacionNegocio.buscarNegocio("661dd3c07afe983885b1783c");
        boolean negocio = cliente.getFavoritos().contains(buscado.getCodigo());
        assertThat(negocio).isEqualTo(true);
    }

    @DisplayName("Test para eliminar un negocio de la lista de favoritos de un cliente")
    @Test
    public void eliminarNegocioFavorito () throws Exception {

        // When - Acción o el comportamiento que se va a probar
        String eliminado = negocioServicio.eliminarNegocioFavorito("661dd3c07afe983885b1783c","661c4b6c03dc96547afaca75");

        //Then - Verificar la salida
        Assertions.assertEquals(eliminado, "El negocio fue eliminado de su lista de favoritos con éxito");
    }

    @DisplayName("Test para mostrar un listado de los negocios favoritos del cliente")
    @Test
    public void listarFavoritosTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        Set<ItemNegocioDTO> lista = negocioServicio.listarFavoritos("661c4b6c03dc96547afaca75");
        //Then - Verificar la salida
        System.out.println("lista.toString() = " + lista.toString());
        Assertions.assertEquals(1, lista.size());
    }

    @DisplayName("Test para mostrar un listado de los negocios favoritos del cliente")
    @Test
    public void obtenerFavoritoTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        DetalleNegocioDTO negocioDTO = negocioServicio.obtenerFavorito("661dd3c07afe983885b1783c", "661c4b6c03dc96547afaca75");

        /*Then - Verificar la salida*/
        System.out.println("negocioDTO = " + negocioDTO.toString());
        assertThat(negocioDTO).isNotNull();
    }

    @DisplayName("Test para adicionar una calificacion a un negocio")
    @Test
    public void calificarNegocioTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        negocioServicio.calificarNegocio("661dd3c07afe983885b1783c", ValorCalificar.FOUR_STAR);

        /*Then - Verificar la salida*/
        Negocio negocio = validacionNegocio.buscarNegocio("661dd3c07afe983885b1783c");
        Assertions.assertEquals(6, negocio.getCalificaciones().size());

    }

    @DisplayName("Test para obtener el valor promedio de todas las calificaciones que tenga un negocio")
    @Test
    public void calcularPromedioCalificacionesTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        int valor = negocioServicio.calcularPromedioCalificaficaciones("661dd3c07afe983885b1783c");

        /*Then - Verificar la salida*/
        System.out.println("valor = " + valor);
        assertThat(valor).isEqualTo(4);
    }

    @DisplayName("Test que segun la fecha actual indica si un negocio esta abierto o cerrado")
    @Test
    public void determinarDisponibilidadNegocioTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        String estado = negocioServicio.determinarDisponibilidadNegocio("661dd3c07afe983885b1783c");

        /*Then - Verificar la salida*/
        System.out.println("El negocio se encuentra " + estado);
        assertThat(estado).isEqualTo("Abierto");
    }

    @DisplayName("Test para buscar un negocio por un nombre dado")
    @Test
    public void buscarNegocioPorNombreTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        DetalleNegocioDTO negocioDTO = negocioServicio.buscarNegocioPorNombre("La Perrada de Ronnie");
        /*Then - Verificar la salida*/
        System.out.println("negocioDTO.toString() = " + negocioDTO.toString());
        assertThat(negocioDTO).isNotNull();
    }

    @DisplayName("Test listar los negocios por segun tipoNegocio esten abiertos o cerrados")
    @Test
    public void listarNegociosAbiertosPorTipoSegunHoraTest() throws Exception {


        /*When - Acción o el comportamiento que se va a probar*/
        List<ItemNegocioDTO> lista = negocioServicio.listarNegociosAbiertosPorTipoSegunHora(TipoNegocio.COMIDAS_RAPIDAS);

        /*Then - Verificar la salida*/
        Assertions.assertEquals(5, lista.size());
    }
}
