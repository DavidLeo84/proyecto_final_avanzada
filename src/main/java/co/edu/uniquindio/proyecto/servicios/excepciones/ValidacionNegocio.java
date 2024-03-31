package co.edu.uniquindio.proyecto.servicios.excepciones;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.modelo.Ubicacion;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidacionNegocio {

    private final NegocioRepo negocioRepo;
    //private final NegocioServicioImpl negocioServicio;

    public void existeNegocio(int latitud, int longitud) throws Exception {

        Optional<Negocio> buscado = negocioRepo.getNegocioByUbicacion(latitud, longitud);
        if (buscado.isPresent() && buscado.get().getEstadoRegistro().equals(EstadoRegistro.ACTIVO)) {
            throw new ResourceNotFoundException("Ya existe un local en las coordenadas seleccionadas");
        }
    }

    public boolean validarCoordenadas(Ubicacion ubicacion) throws Exception{

        List<Negocio> negocioOptional = negocioRepo.getListNegocioByUbicacion(ubicacion.getLatitud(), ubicacion.getLongitud());

        if (negocioOptional.size() > 1) {
            return true;
        }
        return false;
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
