package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.RegistroComentarioDTO;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Comentario;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionComentario;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionNegocio;
import co.edu.uniquindio.proyecto.servicios.interfaces.IComentarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ComentarioServicioImpl implements IComentarioServicio {

    private final ComentarioRepo comentarioRepo;
    private final NegocioRepo negocioRepo;
    private final ClienteRepo clienteRepo;
    private final ValidacionNegocio validacionNegocio;
    private final ValidacionCliente validacionCliente;
    private final ValidacionComentario validacionComentario;

    @Override
    public Comentario crearComentario(RegistroComentarioDTO comentarioDTO) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(comentarioDTO.codigoCliente());
        Negocio negocio = validacionNegocio.buscarNegocio(comentarioDTO.codigoNegocio());
        String fecha = validacionComentario.validarFecha(comentarioDTO.fecha());
        Comentario nuevo = Comentario.builder()
                .codigoCliente(comentarioDTO.codigoCliente()).codigoNegocio(comentarioDTO.codigoNegocio())
                .mensaje(comentarioDTO.mensaje()).respuesta(comentarioDTO.respuesta())
                .fecha(fecha).build();
        comentarioRepo.save(nuevo);
        return nuevo;
    }

    @Override
    public void responderComentario(RegistroComentarioDTO comentarioDTO, String codigo) throws Exception {



    }

    @Override
    public void listarComentariosNegocio() throws Exception {

    }

    @Override
    public void calcularPromedioCalificaciones() throws Exception {

    }

    @Override
    public void darMegusta() throws Exception {

    }

    @Override
    public void calcularCantidadMegusta() throws Exception {

    }
}
