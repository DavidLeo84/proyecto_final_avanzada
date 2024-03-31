package co.edu.uniquindio.proyecto.servicios.excepciones;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidacionModerador {

    private final ModeradorRepo moderadorRepo;
    private final NegocioRepo negocioRepo;
    private final ClienteRepo clienteRepo;

    public String existeEmail(String email) throws Exception {

        Optional<Moderador> moderadorOptional = moderadorRepo.findByEmail(email);
        Moderador moderador = moderadorOptional.get();
        if (!moderadorOptional.isPresent()) {
            throw new Exception("El usuario moderador con email " + email + " no se encuentra registrado");
        }
        return moderador.getEmail();
    }


    public Moderador buscarModerador(String codigoModerador) throws Exception {

        Optional<Moderador> buscado = moderadorRepo.findByCodigo(codigoModerador);
        Moderador moderador = null;

        if (buscado != null && buscado.get().getEstadoRegistro().equals(EstadoRegistro.ACTIVO)) {
            moderador = buscado.get();
        }
        if (buscado == null || buscado.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)) {
            throw new ResourceNotFoundException("No existe usuario moderador con el codigo " + codigoModerador);
        }
        return moderador;
    }

    public Negocio buscarNegocioPendiente(String codigoNegocio) throws Exception {

        Optional<Negocio> buscado = negocioRepo.findByCodigo(codigoNegocio);

        if (buscado == null || buscado.get().getEstadoRegistro().equals(EstadoRegistro.ELIMINADO) ||
                buscado.get().getEstadoRegistro().equals(EstadoRegistro.ACTIVO)) {
            throw new ResourceNotFoundException("No existe negocio pendiente de aprobar con el codigo " + codigoNegocio);
        }
        Negocio negocio = buscado.get();
        return negocio;
    }
}
