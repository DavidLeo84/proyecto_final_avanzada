package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
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


@SpringBootTest
public class ModeradorTest {

    @Autowired
    private ClienteServicioImpl clienteServicio;
    @Autowired
    private ValidacionCliente validacionCliente;
    @Autowired
    ModeradorRepo moderadorRepo;

    @DisplayName("Test para eliminar la cuenta de un moderador")
    @Test
    public void eliminarCuentaTest() throws Exception {

        // Given - Dado o condicion previa o configuración
        Moderador moderador = validacionCliente.buscarModerador("3");

        // When - Acción o el comportamiento que se va a probar
        clienteServicio.eliminarCuenta(moderador.getCodigo());

        //Then - Verificar la salida
        assertThrows(ResourceNotFoundException.class, () -> validacionCliente.buscarModerador("3"));
    }
}
