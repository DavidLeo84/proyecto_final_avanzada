package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.modelo.Cliente;
import co.edu.uniquindio.proyecto.modelo.Cuenta;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ClienteTest {

    @Autowired
    private ClienteRepo clienteRepo;

    @Test
    public void registrarClienteTest(){

        //Creamos el cliente con sus propiedades
        Cliente cliente = Cliente.builder()
                .cedula("1213444")
                .ciudad("Armenia")
                .nickname("Pepitin90")
                .fotoPerfil("")
                .nombre("Pepito Perez")
                .email("pepitoperez@correo.com")
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .password("123456")
                .build();
        //Guardamos el cliente
        Cliente registro = clienteRepo.save( cliente );
//Verificamos que se haya guardado validando que no sea null
        Assertions.assertNotNull(registro);
    }

    @Test
    public void actualizarClienteTest(){
//Obtenemos el cliente con el id XXXXXXX
        Cliente cliente = clienteRepo.findById("1213444").orElseThrow();
//Modificar el email del cliente
        cliente.setEmail("nuevoemail@email.com");
//Guardamos el cliente
        clienteRepo.save( cliente );
//Obtenemos el cliente con el id XXXXXXX nuevamente
        Cliente clienteActualizado = clienteRepo.findById("1213444").orElseThrow();;
//Verificamos que el email se haya actualizado
        Assertions.assertEquals("nuevoemail@email.com", clienteActualizado.getEmail());
    }

}
