package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.DetalleComentarioDTO;
import co.edu.uniquindio.proyecto.dtos.ItemComentarioDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroComentarioDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroRespuestaComentarioDTO;
import co.edu.uniquindio.proyecto.modelo.documentos.Comentario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IComentarioServicio {

    Comentario crearComentario(RegistroComentarioDTO comentarioDTO) throws Exception;

    Comentario responderComentario(RegistroRespuestaComentarioDTO comentarioDTO) throws Exception;

    List<ItemComentarioDTO> listarComentariosNegocio(String codigoNegocio) throws Exception;

    public DetalleComentarioDTO obtenerComentarioNegocio(String codigoComentario) throws Exception;

    void aprobarComentario(String codigoComentario, String codigoCliente) throws Exception; //funcionalidad adicional

//    int calcularCantidadMegusta(String codigoComentario) throws Exception;
}
