package co.edu.uniquindio.proyecto.servicios.excepciones;

import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Comentario;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidacionComentario {

    private final ComentarioRepo comentarioRepo;
    private final ClienteRepo clienteRepo;
    private ValidacionCliente validacionCliente;

   /* public String validarFecha(LocalDateTime fecha) throws Exception {

        if (fecha.isAfter(LocalDateTime.now())) {
            throw new ResourceInvalidException("Fecha no válida");
        }
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a", Locale.ENGLISH);
        String fechaFormateada = formatoFecha.format(fecha);
        return fechaFormateada;
    }*/

    public Comentario validarComentario(String codigoComentario) throws Exception {

        Optional<Comentario> comentarioOptional = comentarioRepo.findByCodigo(codigoComentario);
        if (comentarioOptional == null) {
            throw new Exception("No existe el comentario");
        }
        return comentarioOptional.get();
    }

    public List<Comentario> validarListaComentariosNegocio(String codigoNegocio) throws Exception {

        List<Comentario> listaComentarios = comentarioRepo.findAllByCodigoNegocio(codigoNegocio);
        if (listaComentarios.isEmpty()) {
            throw new ResourceNotFoundException("Este negocio aún no tiene comentarios");
        }
        return listaComentarios;
    }

    public void validarAprobacion(Cliente cliente, String codigoComentario) throws Exception {

        for (String codigo : cliente.getAprobacionesComentarios()) {
            if (codigo.equals(codigoComentario)) {
                throw new ResourceNotFoundException("Este comentario ya fue aprobado con me gusta");
            }
        }
    }
}
