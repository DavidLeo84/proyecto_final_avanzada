package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.ActualizarNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.DetalleNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroHistorialDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroNegocioDTO;
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

    DetalleNegocioDTO buscarNegocio(String codigoNegocio)throws Exception;

    Set<DetalleNegocioDTO> filtrarPorEstado(EstadoRegistro estado)throws Exception;

    Set<DetalleNegocioDTO> listarNegociosPropietario(String codigoCliente)throws Exception;

    void cambiarEstado(String codigoNegocio, EstadoRegistro estado)throws Exception;

    void guardarRecomendados(String codigoNegocio, String codigoCliente) throws Exception;

    DetalleNegocioDTO obtenerRecomendado(String codigoCliente, String codigoNegocio) throws Exception;

    Set<DetalleNegocioDTO> listarRecomendados(String codigoCliente) throws Exception; // funcionalidad adicional

    void guardarNegocioFavorito(String codigoNegocio, String codigoCliente) throws Exception;

    Set<DetalleNegocioDTO> listarFavoritos(String codigoCliente) throws Exception;

    DetalleNegocioDTO obtenerFavorito(String codigoCliente) throws Exception;

    void calificarNegocio(String codigoNegocio, ValorCalificar calificacion) throws Exception;

    float calcularPromedioCalificaficaciones(String codigoNegocio) throws Exception;

}
