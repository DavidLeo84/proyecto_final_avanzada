package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dtos.HistorialRevisionDTO;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.servicios.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.ModeradorServicioImpl;
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
    @Autowired
    private ModeradorServicioImpl moderadorServicio;

    @DisplayName("Test para eliminar la cuenta de un moderador")
    @Test
    public void eliminarCuentaTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        Moderador moderador = validacionCliente.buscarModerador("3");

        /*When - Acción o el comportamiento que se va a probar*/
        clienteServicio.eliminarCuenta(moderador.getCodigo());

        /*Then - Verificar la salida*/
        assertThrows(ResourceNotFoundException.class, () -> validacionCliente.buscarModerador("3"));
    }

    @DisplayName("Test para registrar una revision a un negocio creado")
    @Test
    public void registrarHistorialRevisionTest() throws Exception {

        /*Given - Dado o condicion previa o configuración*/
        HistorialRevisionDTO revisionDTO = new HistorialRevisionDTO(
                "Su negocio cumple con las normas de la aplicación",
                EstadoNegocio.APROBADO,
                "1",
                "6608438bfd6d342c8005bdc8"
        );
        /*When - Acción o el comportamiento que se va a probar*/
        moderadorServicio.revisarNegocio(revisionDTO);

        /*Then - Verificar la salida*/
          System.out.println("revisionDTO = " + revisionDTO);
//        assertThat(revision).isNotNull();


    }
}
