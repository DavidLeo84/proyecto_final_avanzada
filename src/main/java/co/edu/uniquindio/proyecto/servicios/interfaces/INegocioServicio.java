package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.ActualizarNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroHistorialDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroNegocioDTO;
import org.springframework.stereotype.Service;

@Service
public interface INegocioServicio {

    void crearNegocio(RegistroNegocioDTO registroNegocioDTO)throws Exception;;

    void actualizarNegocio(ActualizarNegocioDTO negocioDTO)throws Exception;;

    void eliminarNegocio(String idNegocio)throws Exception;;

    void buscarNegocios(String codigo)throws Exception;;

    void filtrarPorEstado(String estado)throws Exception;;

    void listarNegociosPropietario(String idUsuario)throws Exception;;

    void cambiarEstado(String idNegocio)throws Exception;;

    void registrarRevision(RegistroHistorialDTO historialDTO)throws Exception;

    void obtenerRecomendados() throws Exception; // funcionalidad adicional

    void guardarNegocioFavorito() throws Exception;

    void guardarRecomendados() throws Exception;
}
