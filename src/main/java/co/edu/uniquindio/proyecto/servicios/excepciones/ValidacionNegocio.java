package co.edu.uniquindio.proyecto.servicios.excepciones;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.modelo.Ubicacion;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidacionNegocio {

    private final NegocioRepo negocioRepo;

    public void existeNegocio(int latitud, int longitud) throws Exception {

        Optional<Negocio> buscado = negocioRepo.getNegocioByUbicacion(latitud, longitud);
        if (buscado.isPresent() && buscado.get().getEstado().equals(EstadoNegocio.APROBADO)) {
            throw new ResourceNotFoundException("Ya existe un local en las coordenadas seleccionadas");
        }
    }

    public Negocio buscarNegocio(String codigoNegocio) throws Exception {

        Optional<Negocio> buscado = negocioRepo.findByCodigo(codigoNegocio);
        Negocio negocio = null;

        if (buscado != null && buscado.get().getEstado().equals(EstadoNegocio.APROBADO)) {
            negocio = buscado.get();
        }
        if (buscado == null || buscado.get().getEstado().equals(EstadoNegocio.RECHAZADO) ||
                buscado.get().getEstado().equals(EstadoNegocio.PENDIENTE) || buscado.get().getEstado().equals(EstadoNegocio.ELIMINADO)) {
            throw new ResourceNotFoundException("No existe el negocio con el codigo " + codigoNegocio);
        }
        return negocio;
    }

    public Negocio buscarNegocioPendiente(String codigoNegocio) throws Exception {

        Optional<Negocio> buscado = negocioRepo.findByCodigo(codigoNegocio);

        if (buscado == null || buscado.get().getEstado().equals(EstadoNegocio.RECHAZADO) ||
                buscado.get().getEstado().equals(EstadoNegocio.APROBADO) ||
                buscado.get().getEstado().equals(EstadoNegocio.ELIMINADO)) {
            throw new ResourceNotFoundException("No existe negocio pendiente de aprobar con el codigo " + codigoNegocio);
        }
        Negocio negocio = buscado.get();
        return negocio;
    }

    public void validarListaNegociosEstado(EstadoNegocio estadoNegocio) throws Exception {

        Set<Negocio> lista = negocioRepo.findAllByEstado(estadoNegocio);
        if (lista.isEmpty()) {
            throw new ResourceNotFoundException("No existen negocios con el estado buscado " + estadoNegocio.name());
        }
    }

}
