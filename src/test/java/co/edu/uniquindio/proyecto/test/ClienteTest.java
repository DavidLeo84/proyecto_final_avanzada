package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dtos.DetalleClienteDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroClienteDTO;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    @DisplayName("Test para guardar o registrar un cliente")
    public void registrarClienteTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        RegistroClienteDTO clienteDTO = new RegistroClienteDTO(
                "Fiona Lucero",
                "foto1.jpg",
                "fiona",
                "fiona@gmail.com",
                "123456",
                "ARMENIA"
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
        DetalleClienteDTO clienteDTO = new DetalleClienteDTO(
                "sacha",
                "RISARALDA",
                "foto1.jpg'"
        );
        // When - Acción o el comportamiento que se va a probar
        Cliente actualizado = clienteServicio.editarPerfil(clienteDTO, "660842f2e1f50b64a6376e3c");

        //Then - Verificar la salida
        System.out.println("actualizado = " + actualizado);
        assertThat(actualizado.getCiudad()).isEqualTo("RISARALDA");
    }

    @DisplayName("Test para eliminar la cuenta de un cliente")
    @Test
    public void eliminarCliente() throws Exception {

        // Given - Dado o condicion previa o configuración
        Cliente cliente = validacion.buscarCliente("660842f2e1f50b64a6376e3c");

        // When - Acción o el comportamiento que se va a probar
        clienteServicio.eliminarPerfil(cliente.getCodigo());

        //Then - Verificar la salida
        assertThrows(ResourceNotFoundException.class, () -> validacion.buscarCliente("660842f2e1f50b64a6376e3c"));

    }

    @DisplayName("Test para buscar y traer un cliente")
    @Test
    public void obtenerClienteTest() throws Exception {

        // Given - Dado o condicion previa o configuración

        // When - Acción o el comportamiento que se va a probar
        DetalleClienteDTO detalleClienteDTO = clienteServicio.obtenerUsuario("660842f2e1f50b64a6376e3c");

        //Then - Verificar la salida
        assertThat(detalleClienteDTO).isNotNull();
        System.out.println("detalleClienteDTO.toString() = " + detalleClienteDTO.toString());
    }
}
