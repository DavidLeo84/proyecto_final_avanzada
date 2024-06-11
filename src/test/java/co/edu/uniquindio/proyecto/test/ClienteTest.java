package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dtos.ActualizarClienteDTO;
import co.edu.uniquindio.proyecto.dtos.CambioPasswordDTO;
import co.edu.uniquindio.proyecto.dtos.DetalleClienteDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroClienteDTO;
import co.edu.uniquindio.proyecto.enums.CiudadEnum;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
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

import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
public class ClienteTest {


    @Autowired
    private ClienteServicioImpl clienteServicio;
    @Autowired
    private ValidacionCliente validacionCliente;

    @Test
    @DisplayName("Test para guardar o registrar un cliente")
    public void registrarClienteTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        RegistroClienteDTO clienteDTO = new RegistroClienteDTO(
                "Paulo Serna",
                "foto1.jpg",
                "pau",
                CiudadEnum.ARMENIA,
                "pauloserna@gmail.com",
                "12345678"

        );
        // When - Acción o el comportamiento que se va a probar
        Cliente cliente = clienteServicio.registrarse(clienteDTO);

        //Then - Verificar la salida
        assertThat(cliente).isNotNull();
    }

    @Test
    @DisplayName("Test para validar el nickname ya existente al registrar un cliente")
    public void registrarClienteErrorNicknameTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        RegistroClienteDTO clienteDTO = new RegistroClienteDTO(
                "Pedro Sanchez",
                "foto1.jpg",
                "pedrito",
                CiudadEnum.ARMENIA,
                "pedrosanchez01@gmail.com",
                "123456"
        );

        //When - Then - Verificar la salida
        assertThrows(ResourceNotFoundException.class, () -> clienteServicio.registrarse(clienteDTO));
    }

    @Test
    @DisplayName("Test para validar el email ya existente al registrar un cliente")
    public void registrarClienteErrorEmailTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        RegistroClienteDTO clienteDTO = new RegistroClienteDTO(
                "Pedro Sanchez",
                "foto1.jpg",
                "pedriuss",
                CiudadEnum.ARMENIA,
                "pedrosanchez@gmail.com",
                "123456"
        );

        //When - Then - Verificar la salida
        assertThrows(ResourceNotFoundException.class, () -> clienteServicio.registrarse(clienteDTO));
    }

    @DisplayName("Test para actualizar la informacion de un cliente")
    @Test
    public void actualizarClienteTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        Cliente buscado = validacionCliente.buscarCliente("661c48abd36eeb64ed610953");
        ActualizarClienteDTO clienteDTO = new ActualizarClienteDTO(

                buscado.getCodigo(),
                buscado.getNombre(),
                buscado.getFotoPerfil(),
                buscado.getEmail(),
                CiudadEnum.PEREIRA
        );
        // When - Acción o el comportamiento que se va a probar
        Cliente actualizado = clienteServicio.actualizarCliente(clienteDTO);
        //Then - Verificar la salida
        assertThat(actualizado.getCiudad()).isEqualTo("PEREIRA");
    }

    @DisplayName("Test para actualizar la informacion y validar el correo de un cliente ")
    @Test
    public void actualizarClienteErrorEmailTest() throws Exception {

        try {
            // Given - Dado o condicion previa o configuración
            Cliente buscado = validacionCliente.buscarCliente("661c48abd36eeb64ed610953");
            ActualizarClienteDTO clienteDTO = new ActualizarClienteDTO(
                    buscado.getCodigo(),
                    buscado.getNombre(),
                    buscado.getFotoPerfil(),
                    "pedroperez@gmail.com",
                    CiudadEnum.valueOf(buscado.getCiudad()));
            //When - Then - Verificar la salida
            assertThrows(NoSuchElementException.class, () -> clienteServicio.actualizarCliente(clienteDTO));

        } catch (NoSuchElementException e) {
            System.out.println("Exception = " + e.getMessage());
        }
    }

    @DisplayName("Test para actualizar la informacion y validar si existe el cliente ")
    @Test
    public void actualizarClienteErrorClienteTest() throws Exception {

        try {
            // Given - Dado o condicion previa o configuración
            Cliente buscado = validacionCliente.buscarCliente("6657512d0d8194272067c6ba");
            ActualizarClienteDTO clienteDTO = new ActualizarClienteDTO(
                    buscado.getCodigo(),
                    buscado.getNombre(),
                    buscado.getFotoPerfil(),
                    buscado.getEmail(),
                    CiudadEnum.valueOf(buscado.getCiudad()));
            //When - Then - Verificar la salida
            assertThrows(ResourceNotFoundException.class, () -> clienteServicio.actualizarCliente(clienteDTO));

        } catch (ResourceNotFoundException e) {
            System.out.println("Exception = " + e.getMessage());
        }
    }

    @DisplayName("Test para eliminar la cuenta de un cliente")
    @Test
    public void eliminarCuentaTest() throws Exception {

        try {
            // Given - Dado o condicion previa o configuración
            Cliente cliente = validacionCliente.buscarCliente("6657512d0d8194272067c6ba");

            //When - Then - Verificar la salida
            assertThrows(ResourceNotFoundException.class, () -> clienteServicio.eliminarCuenta("6657512d0d8194272067c6ba"));
        } catch (ResourceNotFoundException e) {
            System.out.println("Exception) = " + e.getMessage());
        }
    }

    @DisplayName("Test validar la lista de negocios activos al eliminar la cuenta de un cliente")
    @Test
    public void eliminarCuentaErrorListaTest() throws Exception {
        try {
            //When - Then - Verificar la salida
            assertThrows(ResourceInvalidException.class, () -> clienteServicio.eliminarCuenta("661c48abd36eeb64ed610953"));
        } catch (ResourceInvalidException e) {
            System.out.println("Exception) = " + e.getMessage());
        }
    }

    @DisplayName("Test para buscar y traer un cliente")
    @Test
    public void obtenerClienteTest() throws Exception {

        // Given - Dado o condicion previa o configuración

        // When - Acción o el comportamiento que se va a probar
        DetalleClienteDTO detalleClienteDTO = clienteServicio.obtenerUsuario("661c48abd36eeb64ed610953");

        //Then - Verificar la salida
        assertThat(detalleClienteDTO).isNotNull();
        System.out.println("detalleClienteDTO.toString() = " + detalleClienteDTO.toString());
    }

    @DisplayName("Test para buscar y traer un cliente")
    @Test
    public void obtenerClienteErrorTest() throws Exception {

        //When - Then - Verificar la salida
        assertThrows(ResourceNotFoundException.class, () -> clienteServicio.obtenerUsuario("661aa51d50a424787193f372"));
    }

    @DisplayName("Test para cambiar el password de un cliente")
    @Test
    public void cambiarPasswordTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        CambioPasswordDTO passwordDTO = new CambioPasswordDTO(
                "87654321",
                "12345678",
                "661c48abd36eeb64ed610953"
        );

        // When - Acción o el comportamiento que se va a probar
        String cambio = clienteServicio.cambiarPassword(passwordDTO);

        //Then - Verificar la salida
        assertThat(cambio).isEqualTo("El password fue cambiado con éxito");
    }

    @DisplayName("Test para validar el password actual al cambiar el password de un cliente")
    @Test
    public void cambiarPasswordErrorTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        CambioPasswordDTO passwordDTO = new CambioPasswordDTO(
                "87654321",
                "12345678",
                "661c48abd36eeb64ed610953"
        );

        // When - Acción o el comportamiento que se va a probar
//        String cambio = clienteServicio.cambiarPassword(passwordDTO);

        //Then - Verificar la salida
        assertThrows(NoSuchElementException.class, () -> clienteServicio.cambiarPassword(passwordDTO));
    }

    @DisplayName("Test para listar las ciudades disponibles para registrarse un cliente")
    @Test
    public void listarCiudadesTest() throws Exception {

        // Given - Dado o condicion previa o configuración

        // When - Acción o el comportamiento que se va a probar
        List<String> listaCiudades = clienteServicio.listarCiudades();

        //Then - Verificar la salida
        assertThat(listaCiudades.size()).isEqualTo(46);
    }
}
