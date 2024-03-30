package co.edu.uniquindio.proyecto.servicios.excepciones;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidacionNegocio {

    private final NegocioRepo negocioRepo;

    public void existeNegocio(int latitud, int longitud) throws Exception {

        Optional<Negocio> buscado = negocioRepo.getNegocioByUbicacion(latitud, longitud);
        if (buscado.isPresent() && buscado.get().getEstadoRegistro().equals(EstadoRegistro.ACTIVO)) {
            throw new ResourceNotFoundException("Ya existe un local en las coordenadas seleccionadas");
        }
    }

    public Negocio buscarNegocio(String codigoNegocio) throws Exception {

        Optional<Negocio> buscado = negocioRepo.findByCodigo(codigoNegocio);
        Negocio negocio = null;

        if (buscado != null && buscado.get().getEstadoRegistro().equals(EstadoRegistro.ACTIVO)) {
            negocio = buscado.get();
        }
        if (buscado == null || buscado.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)) {
            throw new ResourceNotFoundException("No existe el negocio con el codigo " + codigoNegocio);
        }
        return negocio;
    }
//pendiente cambiar el estado registro por el estado negocio
    public Negocio buscarNegocioPendiente(String codigoNegocio) throws Exception {

        Optional<Negocio> buscado = negocioRepo.findByCodigo(codigoNegocio);

        if (buscado == null) {
            throw new ResourceNotFoundException("No existe negocio pendiente de aprobar con el codigo " + codigoNegocio);
        }
        Negocio negocio = buscado.get();
        return negocio;
    }

    public void validarListaNegociosEstado(EstadoRegistro estadoRegistro) throws Exception {

        Set<Negocio> lista = negocioRepo.findAllByEstadoRegistro(estadoRegistro);
        if (lista.isEmpty()) {
            throw new ResourceNotFoundException("No existen negocios con el estado " + estadoRegistro.name());
        }
    }

    public void validarCalificacion(int calificacion) throws Exception {

        if (calificacion < 0 || calificacion > 5) {
            throw new ResourceInvalidException("Valor no válido para calificar sitio");
        }
    }

}
