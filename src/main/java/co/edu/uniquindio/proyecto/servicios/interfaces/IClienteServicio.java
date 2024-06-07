package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface IClienteServicio  {

//    TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception;

    void eliminarCuenta(String codigoCliente) throws Exception;

    String cambiarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception;

    Cliente registrarse(RegistroClienteDTO registroClienteDTO)throws Exception;

    Cliente actualizarCliente(ActualizarClienteDTO clienteDTO)throws Exception;

    DetalleClienteDTO obtenerUsuario(String codigo) throws Exception;

    List<String> listarCiudades() throws Exception;

}
