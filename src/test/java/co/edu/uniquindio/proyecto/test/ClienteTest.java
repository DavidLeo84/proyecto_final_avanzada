package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dtos.ActualizarClienteDTO;
import co.edu.uniquindio.proyecto.dtos.CambioPasswordDTO;
import co.edu.uniquindio.proyecto.dtos.DetalleClienteDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroClienteDTO;
import co.edu.uniquindio.proyecto.enums.CiudadEnum;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceInvalidException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class ClienteTest {

    @Autowired
    private ClienteRepo clienteRepo;
    @Autowired
    private ModeradorRepo moderadorRepo;
    @Autowired
    private ClienteServicioImpl clienteServicio;
    @Autowired
    private ValidacionCliente validacion;
    @Autowired
    private NegocioServicioImpl negocioServicioImpl;

    @Test
    @DisplayName("Test para guardar o registrar un cliente")
    public void registrarClienteTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        RegistroClienteDTO clienteDTO = new RegistroClienteDTO(
                "Pedro Sanchez",
                "foto1.jpg",
                "pedrito",
                CiudadEnum.ARMENIA,
                "pedrosanchez@gmail.com",
                "123456"

        );
        // When - Acción o el comportamiento que se va a probar
        Cliente cliente = clienteServicio.registrarse(clienteDTO);

        //Then - Verificar la salida
        assertThat(cliente).isNotNull();
    }

    @DisplayName("Test para actualizar la informacion de un cliente")
    @Test
    public void actualizarClienteTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        ActualizarClienteDTO clienteDTO = new ActualizarClienteDTO(

                "661c48abd36eeb64ed610953",
                "Maria Cano",
                "foto1.jpg",
                "marycano@gmail.com",
                CiudadEnum.PEREIRA
        );
        // When - Acción o el comportamiento que se va a probar
        Cliente actualizado = clienteServicio.actualizarCliente(clienteDTO);

        //Then - Verificar la salida
        System.out.println("actualizado = " + actualizado);
        assertThat(actualizado.getCiudad()).isEqualTo("RISARALDA");
    }

    @DisplayName("Test para eliminar la cuenta de un cliente")
    @Test
    public void eliminarCuentaTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        Cliente cliente = validacion.buscarCliente("660862be705e055490c3753c");

        // When - Acción o el comportamiento que se va a probar
        clienteServicio.eliminarCuenta(cliente.getCodigo());

        //Then - Verificar la salida
        assertThrows(ResourceNotFoundException.class, () -> validacion.buscarCliente("660862be705e055490c3753c"));

    }

    @DisplayName("Test para buscar y traer un cliente")
    @Test
    public void obtenerClienteTest() throws Exception {

        // Given - Dado o condicion previa o configuración

        // When - Acción o el comportamiento que se va a probar
        DetalleClienteDTO detalleClienteDTO = clienteServicio.obtenerUsuario("661aa51d50a424787193f372");

        //Then - Verificar la salida
        assertThat(detalleClienteDTO).isNotNull();
        System.out.println("detalleClienteDTO.toString() = " + detalleClienteDTO.toString());
    }

    @DisplayName("Test para cambiar el password de un cliente")
    @Test
    public void cambiarPasswordTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        CambioPasswordDTO passwordDTO = new CambioPasswordDTO(
                "654321",
                "661aa51d50a424787193f372"
        );

        // When - Acción o el comportamiento que se va a probar
        String cambio = clienteServicio.cambiarPassword(passwordDTO);

        //Then - Verificar la salida
        assertThat(cambio).isEqualTo("El password fue cambiado con éxito");
    }

    @DisplayName("Test para listar las ciudades donde hayan negocios registrados segun los clientes dueños")
    @Test
    public void listarCiudadesTest() throws Exception {

        // Given - Dado o condicion previa o configuración

        // When - Acción o el comportamiento que se va a probar
        List<String> listaCiudades = clienteServicio.listarCiudades();

        //Then - Verificar la salida
        assertThat(listaCiudades.size()).isEqualTo(2);
    }
}
