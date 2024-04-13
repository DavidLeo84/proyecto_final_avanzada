package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.*;
import org.springframework.stereotype.Service;

@Service
public interface ICuentaServicio {

    TokenDTO iniciarSesion(LoginDTO loginDTO)throws Exception;
    void eliminarCuenta(String codigo)throws Exception;
    TokenDTO enviarLinkRecuperacion(String destinatario)throws Exception;
    String cambiarPassword(CambioPasswordDTO cambioPasswordDTO)throws Exception;
}
