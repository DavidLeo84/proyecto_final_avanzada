package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
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

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

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


    //private List<HistorialRevision> revisiones = new ArrayList<>();
    private Set<Horario> horarios = new HashSet<>();
    private Set<String> telefonos = new HashSet<>();
    private Set<String> imagenes = new HashSet<>();


    @BeforeEach
    void setup() {

        telefonos.add("3102647656");
        telefonos.add("3018967657");

        imagenes.add("cloudinary_1.com");
        imagenes.add("cloudinary_2.com");

        //DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss a", Locale.ENGLISH);
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.ENGLISH);

        LocalTime horaInicio = LocalTime.of(10, 00, 00);
        String abierto = formatoHora.format(horaInicio);

        LocalTime horaFin = LocalTime.of(21, 00, 00);
        String cierre = formatoHora.format(horaFin);

        horarios.add(new Horario("Lunes", abierto, cierre));
    }

    @DisplayName("Test para guardar o registrar un negocio")
    @Test
    public void registrarNegocioTest() throws Exception {

// Given - Dado o condicion previa o configuración
        RegistroNegocioDTO negocioDTO = new RegistroNegocioDTO(
                "La Segunda Perrada de Ronnie",
                "660606f92a21ae6f58cee4ef",
                new Ubicacion(293454789, 657654323),
                "Los mejores perros calientes de la ciudad",
                TipoNegocio.COMIDAS_RAPIDAS.name(),
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
        ActualizarNegocioDTO negocioDTO = new ActualizarNegocioDTO(
                "Los mejores perros calientes y hamburguesas de la ciudad",
                horarios,
                telefonos,
                imagenes
        );
        // When - Acción o el comportamiento que se va a probar
        negocioServicio.actualizarNegocio(negocioDTO, "66060bcfd1889926b1368abe");

        //Then - Verificar la salida
        Negocio negocio = validacionNegocio.buscarNegocio("66060bcfd1889926b1368abe");
        assertThat(negocio.getDescripcion()).isEqualTo("Los mejores perros calientes y hamburguesas de la ciudad");
    }

    @DisplayName("Test para eliminar un negocio")
    @Test
    public void eliminarNegocioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        Negocio negocio = validacionNegocio.buscarNegocio("6605c0613acaae4b06df1685");

        // When - Acción o el comportamiento que se va a probar
        negocioServicio.eliminarNegocio(negocio.getCodigo());

        //Then - Verificar la salida
        assertThrows(ResourceNotFoundException.class, () -> validacionNegocio.buscarNegocio("6605c0613acaae4b06df1685"));
    }

    @DisplayName("Test para buscar un negocio")
    @Test
    public void buscarNegocioTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        Negocio negocio = validacionNegocio.buscarNegocio("66043cd4c375804c01dcdc34");

        // When - Acción o el comportamiento que se va a probar
        DetalleNegocioDTO negocioDTO = negocioServicio.buscarNegocio("66043cd4c375804c01dcdc34");

        //Then - Verificar la salida
        assertThat(negocioDTO).isNotNull();
        System.out.println("negocioDTO.toString() = " + negocioDTO.toString());
    }

    @DisplayName("Test para mostrar una lista de negocios buscados por un estado en especifico")
    @Test
    public void filtrarPorEstadoTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        Set<DetalleNegocioDTO> lista = negocioServicio.filtrarPorEstado(EstadoNegocio.APROBADO);

        //Then - Verificar la salida
        Assertions.assertEquals(4, lista.size());
    }

    @DisplayName("Test para mostrar una lista de negocios buscados por el codigo de un cliente")
    @Test
    public void listarNegociosPropietarioTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        Set<DetalleNegocioDTO> lista = negocioServicio.listarNegociosPropietario("66036f5755eead3f8b558a3e");

        //Then - Verificar la salida
        Assertions.assertEquals(9, lista.size());
    }

    @DisplayName("Test para cambiar el estado de un negocio por parte de un moderador")
    @Test
    public void cambiarEstadoTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        negocioServicio.cambiarEstado("6603aaa5837f132d3d57dff1", EstadoNegocio.ELIMINADO);

        //Then - Verificar la salida
        assertThrows(ResourceNotFoundException.class, () -> validacionNegocio.buscarNegocio("6603aaa5837f132d3d57dff1"));
    }

    @DisplayName("Test para guardar en una lista los negocios que recomienda")
    @Test
    public void guardarRecomendadosTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        negocioServicio.guardarRecomendados("66061a5dcd9e2812ba7c5cb6", "660606f92a21ae6f58cee4ef");

        //Then - Verificar la salida
        Cliente cliente = validacionCliente.buscarCliente("660606f92a21ae6f58cee4ef");
        Assertions.assertEquals(2, cliente.getRecomendados().size());
    }

    @DisplayName("Test para mostrar un negocio de la lista de recomendados del cliente")
    @Test
    public void obtenerRecomendadosTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        DetalleNegocioDTO negocioDTO = negocioServicio.obtenerRecomendado("660606f92a21ae6f58cee4ef", "6605c0613acaae4b06df1685");

        //Then - Verificar la salida
        assertThat(negocioDTO).isNotNull();
    }

    @DisplayName("Test para mostrar una lista de negocios recomendados del cliente")
    @Test
    public void listarRecomendadosTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        Set<DetalleNegocioDTO> lista = negocioServicio.listarRecomendados("660606f92a21ae6f58cee4ef");

        //Then - Verificar la salida
        Assertions.assertEquals(1, lista.size());
    }

    @DisplayName("Test para guardar el codigo del negocio en la lista de favoritos del cliente")
    @Test
    public void guardarNegocioFavoritoTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        negocioServicio.guardarNegocioFavorito("6605c0613acaae4b06df1685", "660606f92a21ae6f58cee4ef");

        //Then - Verificar la salida
        Cliente cliente = validacionCliente.buscarCliente("660606f92a21ae6f58cee4ef");
        Negocio buscado = validacionNegocio.buscarNegocio("6605c0613acaae4b06df1685");
        boolean negocio = cliente.getFavoritos().contains(buscado.getCodigo());
        assertThat(negocio).isEqualTo(true);
    }

    @DisplayName("Test para mostrar un listado de los negocios favoritos del cliente")
    @Test
    public void listarFavoritosTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        Set<DetalleNegocioDTO> lista = negocioServicio.listarFavoritos("660606f92a21ae6f58cee4ef");
        //Then - Verificar la salida
        System.out.println("lista.toString() = " + lista.toString());
        Assertions.assertEquals(1, lista.size());
    }

    @DisplayName("Test para mostrar un listado de los negocios favoritos del cliente")
    @Test
    public void obtenerFavoritoTest() throws Exception {

        // When - Acción o el comportamiento que se va a probar
        DetalleNegocioDTO negocioDTO = negocioServicio.obtenerFavorito("660606f92a21ae6f58cee4ef");

        //Then - Verificar la salida
        System.out.println("negocioDTO.toString() = " + negocioDTO.toString());
        assertThat(negocioDTO).isNotNull();
    }
}
