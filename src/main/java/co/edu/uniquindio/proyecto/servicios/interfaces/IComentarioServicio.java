package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.RegistroComentarioDTO;
import co.edu.uniquindio.proyecto.modelo.documentos.Comentario;
import org.springframework.stereotype.Service;

@Service
public interface IComentarioServicio {

    Comentario crearComentario(RegistroComentarioDTO comentarioDTO) throws Exception;;

    void responderComentario(RegistroComentarioDTO comentarioDTO, String codigo) throws Exception;;

    void listarComentariosNegocio() throws Exception;;

    void calcularPromedioCalificaciones() throws Exception;;

    void darMegusta() throws Exception; //funcionalidad adicional

    void calcularCantidadMegusta() throws Exception;
}
