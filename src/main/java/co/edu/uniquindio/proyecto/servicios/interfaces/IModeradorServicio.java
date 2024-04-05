package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.DetalleNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.HistorialRevisionDTO;
import co.edu.uniquindio.proyecto.dtos.ItemNegocioDTO;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface IModeradorServicio extends ICuentaServicio {


    void revisarNegocio(HistorialRevisionDTO revisionDTO) throws Exception;

    void eliminarNegocioCaducado(String codigoNegocio) throws Exception;

    Negocio obtenerNegocioAprobado(ItemNegocioDTO negocioDTO) throws Exception;

    Negocio obtenerNegocioPendiente(ItemNegocioDTO negocioDTO) throws Exception;

    Negocio obtenerNegocioRechazado(ItemNegocioDTO negocioDTO) throws Exception;

    Negocio obtenerNegocioEliminado(ItemNegocioDTO negocioDTO) throws Exception;

    List<ItemNegocioDTO> listarNegociosAprobados() throws Exception;

    List<ItemNegocioDTO> listarNegociosPendientes() throws Exception;

    List<ItemNegocioDTO> listarNegociosRechazados() throws Exception;

    List<ItemNegocioDTO> listarNegociosEliminados() throws Exception;

}
