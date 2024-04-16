package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.DetalleComentarioDTO;
import co.edu.uniquindio.proyecto.dtos.ItemNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroComentarioDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroRespuestaComentarioDTO;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Comentario;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.*;
import co.edu.uniquindio.proyecto.servicios.interfaces.IComentarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioServicioImpl implements IComentarioServicio {

    private final ComentarioRepo comentarioRepo;
    private final NegocioRepo negocioRepo;
    private final ClienteRepo clienteRepo;
    private final ValidacionNegocio validacionNegocio;
    private final ValidacionCliente validacionCliente;
    private final ValidacionComentario validacionComentario;
    private final ValidacionModerador validacionModerador;

    @Override
    public Comentario crearComentario(RegistroComentarioDTO comentarioDTO) throws Exception {

            Cliente cliente = validacionCliente.buscarCliente(comentarioDTO.codigoCliente());
            Negocio negocio = validacionNegocio.validarNegocioAprobado(comentarioDTO.codigoNegocio());
            Comentario nuevo = Comentario.builder()
                    .codigoCliente(comentarioDTO.codigoCliente()).codigoNegocio(comentarioDTO.codigoNegocio())
                    .mensaje(comentarioDTO.mensaje()).fechaMensaje(validacionModerador.formatearFecha(comentarioDTO.fechaMensaje()))
                    .respuesta("").fechaRespuesta("").build();
            comentarioRepo.save(nuevo);
            return nuevo;

    }

    @Override
    public Comentario responderComentario(RegistroRespuestaComentarioDTO comentarioDTO) throws Exception {

        try {
            Cliente cliente = validacionCliente.buscarCliente(comentarioDTO.codigoCliente());
            Negocio negocio = validacionNegocio.validarNegocioAprobado(comentarioDTO.codigoNegocio());
            Comentario comentario = validacionComentario.validarComentario(comentarioDTO.codigoComentario());
            Comentario respuesta = Comentario.builder()
                    .codigo(comentarioDTO.codigoComentario()).codigoCliente(comentarioDTO.codigoCliente())
                    .codigoNegocio(comentarioDTO.codigoNegocio()).mensaje(comentarioDTO.mensaje())
                    .fechaMensaje(comentarioDTO.fechaMensaje()).respuesta(comentarioDTO.respuesta())
                    .fechaRespuesta(validacionModerador.formatearFecha(comentarioDTO.fechaRespuesta()))
                    .meGusta(new ArrayList<>()).build();
            comentarioRepo.save(respuesta);
            return respuesta;
        }
        catch (RuntimeException ex){
            throw new RuntimeException("No se puede responder a un comentario que no existe");
        }
    }

    @Override
    public List<DetalleComentarioDTO> listarComentariosNegocio(String codigoNegocio) throws Exception {

        List<Comentario> comentarios = validacionComentario.validarListaComentariosNegocio(codigoNegocio);
        return comentarios.stream().map(c -> new DetalleComentarioDTO(
                c.getMensaje(), c.getFechaMensaje(), c.getRespuesta())).collect(Collectors.toList());
    }

    /*@Override
    public void calcularPromedioCalificaciones() throws Exception {

    }*/

    @Override
    public void aprobarComentario(String codigoComentario, String codigoCliente) throws Exception {

        Comentario comentario = validacionComentario.validarComentario(codigoComentario);
        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        validacionComentario.validarAprobacion(cliente, codigoComentario);
            cliente.getAprobacionesComentarios().add(codigoComentario);
            comentario.getMeGusta().add(1);
            clienteRepo.save(cliente);
            comentarioRepo.save(comentario);
    }

    @Override
    public int calcularCantidadMegusta(String codigoComentario) throws Exception {

        Comentario comentario = validacionComentario.validarComentario(codigoComentario);
        return comentario.getMeGusta().size();
    }
}
