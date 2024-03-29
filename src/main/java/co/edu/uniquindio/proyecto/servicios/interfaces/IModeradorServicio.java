package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.HistorialRevisionDTO;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import org.springframework.stereotype.Service;

@Service
public interface IModeradorServicio extends ICuentaServicio {


    HistorialRevision revisarNegocio(HistorialRevisionDTO revisionDTO) throws Exception;

    void eliminarNegocioCaducado(String codigo) throws Exception;

}
