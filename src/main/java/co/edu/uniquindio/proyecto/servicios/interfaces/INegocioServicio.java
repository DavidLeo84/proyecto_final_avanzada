package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.enums.ValorCalificar;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
public interface INegocioServicio {

    Negocio crearNegocio(RegistroNegocioDTO registroNegocioDTO)throws Exception;

    void actualizarNegocio(ActualizarNegocioDTO negocioDTO)throws Exception;

    void eliminarNegocio(String codigoNegocio)throws Exception;

    DetalleNegocioDTO obtenerNegocio(String codigoNegocio)throws Exception;

    List<ItemNegocioDTO> filtrarPorEstado(EstadoNegocio estado)throws Exception;

    List<ItemNegocioDTO> listarNegociosPropietario(String codigoCliente)throws Exception;

    //void cambiarEstado(String codigoNegocio, EstadoNegocio estado)throws Exception;

    void guardarRecomendados(String codigoNegocio, String codigoCliente) throws Exception;

    DetalleNegocioDTO obtenerRecomendado(String codigoCliente, String codigoNegocio) throws Exception;

    String eliminarNegocioRecomendado(String codigoNegocio, String codigoCliente) throws Exception;

    Set<ItemNegocioDTO> listarRecomendadosCliente(String codigoCliente) throws Exception; // funcionalidad adicional

    List<ItemNegocioDTO> listaNegociosRecomendadosPorClientes() throws Exception;

    DetalleRevisionDTO obtenerRevision(ItemRevisionDTO item) throws Exception;

    List<ItemRevisionDTO> listarRevisiones(String codigoNegocio) throws Exception;

    void guardarNegocioFavorito(String codigoNegocio, String codigoCliente) throws Exception;

    String eliminarNegocioFavorito(String codigoNegocio, String codigoCliente) throws Exception;

    Set<ItemNegocioDTO> listarFavoritos(String codigoCliente) throws Exception;

    DetalleNegocioDTO obtenerFavorito(String codigoCliente) throws Exception;

    void calificarNegocio(String codigoNegocio, ValorCalificar calificacion) throws Exception;

    float calcularPromedioCalificaficaciones(String codigoNegocio) throws Exception;

    String determinarDisponibilidadNegocio(String codigoNegocio) throws Exception;

    DetalleNegocioDTO buscarNegocioPorNombre(String nombreNegocio) throws Exception;

    List<ItemNegocioDTO> listarNegociosAbiertosPorTipoSegunHora(TipoNegocio tipoNegocio) throws Exception;

}
