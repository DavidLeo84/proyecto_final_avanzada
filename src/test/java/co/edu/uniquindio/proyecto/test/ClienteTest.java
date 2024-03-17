package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dtos.RegistroClienteDTO;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.Rol;
import co.edu.uniquindio.proyecto.modelo.DetalleProducto;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.excepciones.Validacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ClienteTest {

    @Autowired
    private ClienteRepo clienteRepo;
    @Autowired
    private ClienteServicioImpl clienteServicio;
    @Autowired
    private Validacion validacion;

    @Test
    public void registrarClienteTest() throws Exception {

        List<String> favoritos = new ArrayList<>();
        List<String> negocios = new ArrayList<>();

        RegistroClienteDTO clienteDTO = new RegistroClienteDTO(
                "leonardo",
                "foto3.jpg",
                "leo",
                "leoromero@gmail.com",
                "123456",
                "ARMENIA",
                favoritos,
                negocios
        );
        int cliente = clienteServicio.registrarse(clienteDTO);
        //Cliente creado = validacion.buscarCliente("pika");
        Assertions.assertNotEquals(0,5);


    }
/*
    @Test
    public void actualizarClienteTest() {


        Cliente cliente = clienteRepo.findByCodigo(2).orElseThrow();
        cliente.setEmail("nuevoemail@email.com");
        clienteRepo.save(cliente);
        Cliente clienteActualizado = clienteRepo.findByCodigo(2).orElseThrow();
        ;
        Assertions.assertEquals("nuevoemail@email.com", clienteActualizado.getEmail());
    }


 */

}
