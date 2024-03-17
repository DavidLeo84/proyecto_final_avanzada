package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.CambioPasswordDTO;
import co.edu.uniquindio.proyecto.dtos.DetalleClienteDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroClienteDTO;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import org.springframework.stereotype.Service;

@Service
public interface IClienteServicio extends ICuentaServicio {

    int registrarse(RegistroClienteDTO registroClienteDTO)throws Exception;

    Cliente editarPerfil(DetalleClienteDTO clienteDTO)throws Exception;

    void eliminarPerfil(String codigo) throws Exception;

    void obtenerUsuario(String codigo) throws Exception;

    void recuperarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception;



}
