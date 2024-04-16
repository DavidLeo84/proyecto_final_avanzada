package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.DetalleComentarioDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroComentarioDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroRespuestaComentarioDTO;
import co.edu.uniquindio.proyecto.modelo.documentos.Comentario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IComentarioServicio {

    Comentario crearComentario(RegistroComentarioDTO comentarioDTO) throws Exception;;

    Comentario responderComentario(RegistroRespuestaComentarioDTO comentarioDTO) throws Exception;;

    List<DetalleComentarioDTO> listarComentariosNegocio(String codigoNegocio) throws Exception;;

    //void calcularPromedioCalificaciones() throws Exception;;

    void aprobarComentario(String codigoComentario, String codigoCliente) throws Exception; //funcionalidad adicional

    int calcularCantidadMegusta(String codigoComentario) throws Exception;
}
