package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.DetalleNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.HistorialRevisionDTO;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IModeradorServicio extends ICuentaServicio {


    void revisarNegocio(HistorialRevisionDTO revisionDTO) throws Exception;

    void eliminarNegocioCaducado(String codigoNegocio) throws Exception;

    void obtenerNegocioPendiente(String codigoNegocio) throws Exception;

    List<DetalleNegocioDTO> listarNegociosPendientes() throws Exception;

    void obtenerNegocioRechazado(String codigoNegocio) throws Exception;

    List<DetalleNegocioDTO> listarNegociosRechazados() throws Exception;

    void obtenerNegocioEliminado(String codigoNegocio) throws Exception;

    List<DetalleNegocioDTO> listarNegociosEliminados() throws Exception;

}
