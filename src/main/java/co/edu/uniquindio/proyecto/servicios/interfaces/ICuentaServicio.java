package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.CambioPasswordDTO;
import co.edu.uniquindio.proyecto.dtos.EmailDTO;
import co.edu.uniquindio.proyecto.dtos.SesionDTO;
import org.springframework.stereotype.Service;

@Service
public interface ICuentaServicio {

    void iniciarSesion(SesionDTO sesionDTO)throws Exception;
    void eliminarCuenta(String codigo)throws Exception;
    void enviarLinkRecuperacion(String destinatario)throws Exception;
    void cambiarPassword(CambioPasswordDTO cambioPasswordDTO)throws Exception;
}
