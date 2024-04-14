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

        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("hh:mm:ss a").withLocale(Locale.ENGLISH);

        LocalTime horaInicio = LocalTime.of(10, 00, 00);
        String abierto = formatoHora.format(horaInicio);

        LocalTime horaFin = LocalTime.of(21, 00, 00);
        String cierre = formatoHora.format(horaFin);

        DayOfWeek dia = LocalDate.now().getDayOfWeek();

        horarios.add(new Horario(dia, abierto, cierre));
    }

    @DisplayName("Test para guardar o registrar un negocio")
    @Test
    public void registrarNegocioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        RegistroNegocioDTO negocioDTO = new RegistroNegocioDTO(
                "La Segunda Perrada de Ronnie",
                "661aa62f1434fa40da4a039a",
                new Ubicacion(144144770, 599865111),
                "Los mejores perros calientes y hamburguesas de la ciudad",
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

    @DisplayName("Test para actualizar un negocio")
    @Test
    public void actualizarNegocioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        Negocio negocio = validacionNegocio.buscarNegocio("6608438bfd6d342c8005bdc8");
        ActualizarNegocioDTO negocioDTO = new ActualizarNegocioDTO(
                "Los mejores perros calientes y hamburguesas del pueblo",
                negocio.getUbicacion(),
                negocio.getHorarios(),
                negocio.getTelefonos(),
                negocio.getImagenes()
        );
        // When - Acción o el comportamiento que se va a probar
        negocioServicio.actualizarNegocio(negocioDTO, "6608438bfd6d342c8005bdc8");

        //Then - Verificar la salida
        Negocio buscado = validacionNegocio.buscarNegocio("6608438bfd6d342c8005bdc8");
        assertThat(buscado.getDescripcion()).isEqualTo("Los mejores perros calientes y hamburguesas del pueblo");
    }

    @DisplayName("Test para eliminar un negocio")
    @Test
    public void eliminarNegocioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        Negocio negocio = validacionNegocio.buscarNegocio("6608438bfd6d342c8005bdc8");

        // When - Acción o el comportamiento que se va a probar
        negocioServicio.eliminarNegocio(negocio.getCodigo());

        //Then - Verificar la salida
        assertThrows(ResourceNotFoundException.class, () -> validacionNegocio.buscarNegocio("6608438bfd6d342c8005bdc8"));
    }

    @DisplayName("Test para buscar un negocio")
    @Test
    public void ObtenerNegocioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        Negocio negocio = validacionNegocio.buscarNegocio("6611f8060d65450fe2d86d4c");

        // When - Acción o el comportamiento que se va a probar
        DetalleNegocioDTO negocioDTO = negocioServicio.obtenerNegocio("6611f8060d65450fe2d86d4c");

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
        Assertions.assertEquals(2, lista.size());
    }

    @DisplayName("Test para mostrar una lista de negocios buscados por el codigo de un cliente")
    @Test
    public void listarNegociosPropietarioTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        Set<ItemNegocioDTO> lista = negocioServicio.listarNegociosPropietario("660842f2e1f50b64a6376e3c");

        //Then - Verificar la salida
        Assertions.assertEquals(2, lista.size());
    }

    /*@DisplayName("Test para cambiar el estado de un negocio por parte de un moderador")
    @Test
    public void cambiarEstadoTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        negocioServicio.cambiarEstado("6608438bfd6d342c8005bdc8", EstadoNegocio.RECHAZADO);

        //Then - Verificar la salida
        assertThrows(ResourceNotFoundException.class, () -> validacionNegocio.buscarNegocio("6608438bfd6d342c8005bdc8"));
    }*/

    @DisplayName("Test para guardar en una lista los negocios que recomienda")
    @Test
    public void guardarRecomendadosTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        negocioServicio.guardarRecomendados("661aacb404561d72bdbf16f2", "661aa6b492e1d716362980a0");

        //Then - Verificar la salida
        Cliente cliente = validacionCliente.buscarCliente("661aa6b492e1d716362980a0");
        Assertions.assertEquals(1, cliente.getRecomendados().size());
    }

    @DisplayName("Test para mostrar un negocio de la lista de recomendados del cliente")
    @Test
    public void obtenerRecomendadosTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        DetalleNegocioDTO negocioDTO = negocioServicio.obtenerRecomendado("660842f2e1f50b64a6376e3c", "6608438bfd6d342c8005bdc8");

        //Then - Verificar la salida
        System.out.println("negocioDTO = " + negocioDTO);
        assertThat(negocioDTO).isNotNull();
    }

    @DisplayName("Test para eliminar un negocio de la lista de recomendados de un cliente")
    @Test
    public void eliminarNegocioRecomendado() throws Exception {

        // Given - Dado o condicion previa o configuración

        // When - Acción o el comportamiento que se va a probar
        String eliminado = negocioServicio.eliminarNegocioRecomendado("661aacb404561d72bdbf16f2","661aa6b492e1d716362980a0");

        //Then - Verificar la salida
        Assertions.assertEquals(eliminado, "El negocio fue eliminado de su lista de recomendados con éxito");

    }

    @DisplayName("Test para mostrar una lista de negocios recomendados del cliente")
    @Test
    public void listarRecomendadosTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        Set<ItemNegocioDTO> lista = negocioServicio.listarRecomendadosCliente("661aa51d50a424787193f372");

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
        ItemRevisionDTO item = new ItemRevisionDTO("661aacb404561d72bdbf16f2", "2024/04/13 11:03:00.000 AM");
        
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
        List<ItemRevisionDTO> revisionDTO = negocioServicio.listarRevisiones("661aacb404561d72bdbf16f2");

        //Then - Verificar la salida
        Assertions.assertEquals(2, revisionDTO.size());

    }

    @DisplayName("Test para guardar el codigo del negocio en la lista de favoritos del cliente")
    @Test
    public void guardarNegocioFavoritoTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        negocioServicio.guardarNegocioFavorito("661aacb404561d72bdbf16f2", "661aa62f1434fa40da4a039a");

        //Then - Verificar la salida
        Cliente cliente = validacionCliente.buscarCliente("661aa62f1434fa40da4a039a");
        Negocio buscado = validacionNegocio.buscarNegocio("661aacb404561d72bdbf16f2");
        boolean negocio = cliente.getFavoritos().contains(buscado.getCodigo());
        assertThat(negocio).isEqualTo(true);
    }

    @DisplayName("Test para eliminar un negocio de la lista de favoritos de un cliente")
    @Test
    public void eliminarNegocioFavorito () throws Exception {

        String eliminado = negocioServicio.eliminarNegocioFavorito("661aacb404561d72bdbf16f2","661aa62f1434fa40da4a039a");

        //Then - Verificar la salida
        Assertions.assertEquals(eliminado, "El negocio fue eliminado de su lista de favoritos con éxito");
    }

    @DisplayName("Test para mostrar un listado de los negocios favoritos del cliente")
    @Test
    public void listarFavoritosTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        Set<ItemNegocioDTO> lista = negocioServicio.listarFavoritos("661aa62f1434fa40da4a039a");
        //Then - Verificar la salida
        System.out.println("lista.toString() = " + lista.toString());
        Assertions.assertEquals(1, lista.size());
    }

    @DisplayName("Test para mostrar un listado de los negocios favoritos del cliente")
    @Test
    public void obtenerFavoritoTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        DetalleNegocioDTO negocioDTO = negocioServicio.obtenerFavorito("660842f2e1f50b64a6376e3c");

        /*Then - Verificar la salida*/
        System.out.println("negocioDTO = " + negocioDTO.toString());
        assertThat(negocioDTO).isNotNull();
    }

    @DisplayName("Test para adicionar una calificacion a un negocio")
    @Test
    public void calificarNegocioTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        negocioServicio.calificarNegocio("661aacb404561d72bdbf16f2", ValorCalificar.FOUR_STAR);

        /*Then - Verificar la salida*/
        Negocio negocio = validacionNegocio.buscarNegocio("661aacb404561d72bdbf16f2");
        Assertions.assertEquals(3, negocio.getCalificaciones().size());

    }

    @DisplayName("Test para obtener el valor promedio de todas las calificaciones que tenga un negocio")
    @Test
    public void calcularPromedioCalificacionesTest() throws Exception {

        /*When - Acción o el comportamiento que se va a probar*/
        float valor = negocioServicio.calcularPromedioCalificaficaciones("661aacb404561d72bdbf16f2");

        /*Then - Verificar la salida*/
        System.out.println("valor = " + valor);
        assertThat(valor).isEqualTo(4.0f);
    }

    @DisplayName("Test que segun la fecha actual indica si un negocio esta abierto o cerrado")
    @Test
    public void determinarDisponibilidadNegocioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        DayOfWeek dia = LocalDate.now().getDayOfWeek();
        LocalTime horaActual = LocalTime.now();

        FechaActualDTO fechaActualDTO = new FechaActualDTO(
                dia,
                horaActual
        );

        /*When - Acción o el comportamiento que se va a probar*/
        String estado = negocioServicio.determinarDisponibilidadNegocio("6608442e31eff35db34b4b8a", fechaActualDTO);

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
}
