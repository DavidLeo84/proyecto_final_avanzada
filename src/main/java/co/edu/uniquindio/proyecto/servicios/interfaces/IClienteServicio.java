package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.ActualizarClienteDTO;
import co.edu.uniquindio.proyecto.dtos.DetalleClienteDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroClienteDTO;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface IClienteServicio extends ICuentaServicio {

    Cliente registrarse(RegistroClienteDTO registroClienteDTO)throws Exception;

    Cliente actualizarCliente(ActualizarClienteDTO clienteDTO)throws Exception;

    //void eliminarPerfil(String codigo) throws Exception;

    DetalleClienteDTO obtenerUsuario(String codigo) throws Exception;

    List<String> listarCiudades() throws Exception;

}
