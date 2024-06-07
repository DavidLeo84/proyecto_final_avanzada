package co.edu.uniquindio.proyecto.servicios.excepciones;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidacionModerador {

    private final ModeradorRepo moderadorRepo;
    private final NegocioRepo negocioRepo;
    private final ClienteRepo clienteRepo;

    public void existeEmail(String email) throws Exception {

        Optional<Moderador> moderadorOptional = moderadorRepo.findByEmail(email);
        if (!moderadorOptional.isPresent()) {
            throw new Exception("El correo no se encuentra registrado");
        }

    }

    public Moderador buscarPorEmail(String email) throws Exception {

        Optional<Moderador> moderadorOptional = moderadorRepo.findByEmail(email);
        if (moderadorOptional.isEmpty()) {
            throw new Exception("El correo no se encuentra registrado");
        }
        Moderador moderador = moderadorOptional.get();
        return moderador;
    }

    public Moderador buscarModerador(String codigoModerador) throws Exception {

        Optional<Moderador> buscado = moderadorRepo.findByCodigo(codigoModerador);
        Moderador moderador = null;

        if (buscado.isPresent() && buscado.get().getEstadoRegistro().equals(EstadoRegistro.ACTIVO)) {
            moderador = buscado.get();
        }
        if (buscado.isEmpty() || buscado.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)) {
            throw new ResourceNotFoundException("No existe usuario moderador");
        }
        return moderador;
    }

    public Negocio buscarNegocioPendiente(String codigoNegocio) throws Exception {

        Optional<Negocio> buscado = negocioRepo.findByCodigo(codigoNegocio);

        if (
                buscado.get().getEstadoNegocio().equals(EstadoNegocio.PENDIENTE)) {
            throw new ResourceNotFoundException("No existe negocio pendiente de aprobar con el codigo " + codigoNegocio);
        }
        Negocio negocio = buscado.get();
        return negocio;
    }

    public String formatearFecha(LocalDateTime fecha) throws Exception {

        if (fecha.isAfter(LocalDateTime.now())) {
            throw new ResourceInvalidException("Fecha no válida");
        }
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss.000 a", Locale.ENGLISH);
        String fechaFormateada = formatoFecha.format(fecha);
        return fechaFormateada;
    }

    public LocalDateTime transformarFecha(String fechaRevision) throws Exception {

        try {
            String fechaString1 = fechaRevision.replaceAll("/", "-");
            String fechaString1_1 = fechaString1.replaceAll(" ", "T");
            DateTimeFormatter formatoFecha = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            TemporalAccessor fechaFormateada = formatoFecha.parse(fechaString1_1.substring(0, 20));
            return LocalDateTime.from(fechaFormateada);
        } catch (Exception ex) {
            throw new Exception("La fecha no cumple con el formato requerido");
        }
    }
}
