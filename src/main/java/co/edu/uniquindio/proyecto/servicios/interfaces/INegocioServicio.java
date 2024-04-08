package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.ValorCalificar;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface INegocioServicio {

    Negocio crearNegocio(RegistroNegocioDTO registroNegocioDTO)throws Exception;

    void actualizarNegocio(ActualizarNegocioDTO negocioDTO, String codigoNegocio)throws Exception;

    void eliminarNegocio(String codigoNegocio)throws Exception;

    DetalleNegocioDTO obtenerNegocio(String codigoNegocio)throws Exception;

    List<ItemNegocioDTO> filtrarPorEstado(EstadoNegocio estado)throws Exception;

    Set<ItemNegocioDTO> listarNegociosPropietario(String codigoCliente)throws Exception;

    //void cambiarEstado(String codigoNegocio, EstadoNegocio estado)throws Exception;

    void guardarRecomendados(String codigoNegocio, String codigoCliente) throws Exception;

    DetalleNegocioDTO obtenerRecomendado(String codigoCliente, String codigoNegocio) throws Exception;

    Set<ItemNegocioDTO> listarRecomendados(String codigoCliente) throws Exception; // funcionalidad adicional

    DetalleRevisionDTO obtenerRevision(ItemRevisionDTO item) throws Exception;

    List<ItemRevisionDTO> listarRevisiones(String codigoNegocio) throws Exception;

    void guardarNegocioFavorito(String codigoNegocio, String codigoCliente) throws Exception;

    Set<ItemNegocioDTO> listarFavoritos(String codigoCliente) throws Exception;

    DetalleNegocioDTO obtenerFavorito(String codigoCliente) throws Exception;

    void calificarNegocio(String codigoNegocio, ValorCalificar calificacion) throws Exception;

    float calcularPromedioCalificaficaciones(String codigoNegocio) throws Exception;

}
