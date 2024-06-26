package co.edu.uniquindio.proyecto.servicios.excepciones;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.enums.ValorCalificar;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.Ubicacion;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ValidacionNegocio {

    private final NegocioRepo negocioRepo;

    /*Metodo para validar las coordenadas del negocio que se va a crear*/
    public void existeCoordenadas(Double latitud, Double longitud) throws Exception {

        List<Negocio> negocioOptional = negocioRepo.getListaNegocioByUbicacion(latitud, longitud);
        if (negocioOptional.size() > 0) {
            throw new ResourceNotFoundException("Ya existe un local en las coordenadas seleccionadas");
        }
    }

    /*metodo para validar las coordenadas que se ingresaron y en la revision se debe de nuevo validar*/
    public boolean validarCoordenadas(Ubicacion ubicacion) throws Exception {

        List<Negocio> negocioOptional = negocioRepo.getListaNegocioByUbicacion(ubicacion.getLatitud(), ubicacion.getLongitud());
        if (negocioOptional.size() > 1) {
            return true;
        }
        return false;
    }

    public Negocio buscarNegocioRechazadoParaModificar(String codigoNegocio) throws Exception {

        Optional<Negocio> buscado = negocioRepo.findByCodigo(codigoNegocio);
        Negocio negocio = null;
        if (buscado.isPresent() && buscado.get().getEstadoNegocio().equals(EstadoNegocio.RECHAZADO)) {
            negocio = buscado.get();
        }
        if (!buscado.isPresent() || !buscado.get().getEstadoNegocio().equals(EstadoNegocio.RECHAZADO)) {
            throw new ResourceNotFoundException("El negocio buscado no se encuentra como rechazado");
        }
        return negocio;
    }

    public Negocio buscarNegocio(String codigoNegocio) throws Exception {

        Optional<Negocio> buscado = negocioRepo.findByCodigo(codigoNegocio);
        Negocio negocio = null;
        if (!buscado.isEmpty() && buscado.get().getEstadoNegocio().equals(EstadoNegocio.APROBADO)) {
            negocio = buscado.get();
        }

        if (buscado.isEmpty() || buscado.get().getEstadoNegocio().equals(EstadoNegocio.ELIMINADO)) {
            throw new ResourceNotFoundException("No existe el negocio");
        }
        return negocio;
    }

    /*Metodo para validar una lista de negocios segun el estado que se solicite */
    public void validarEstadoListaNegocios(EstadoNegocio estado) throws Exception {

        List<Negocio> lista = negocioRepo.findAllByEstadoNegocio(estado);
        if (lista.isEmpty()) {
            throw new ResourceNotFoundException("No existen negocios con el estado " + estado.name());
        }
    }

    /*Metodo que valida la existencia de un negocio que tenga como estadoNegocio aprobado*/
    public Negocio validarNegocioAprobado(String codigoNegocio) throws Exception {

        try {
            Optional<Negocio> negocioOptional = negocioRepo.findByCodigo(codigoNegocio);
            if (!negocioOptional.get().getEstadoNegocio().equals(EstadoNegocio.APROBADO) || !negocioOptional.isPresent()) {
                throw new ResourceNotFoundException("No existe el negocio solicitado como " + EstadoNegocio.APROBADO.name());
            }
            Negocio negocio = negocioOptional.get();
            return negocio;
        } catch (RuntimeException ex) {
            throw new RuntimeException("codigo negocio no es válido o no corresponde", ex);
        }
    }

    /*Metodo que valida la existencia de un negocio que tenga como estadoNegocio igual a pendiente*/
    public Negocio validarNegocioPendiente(String codigoNegocio) throws Exception {

        try {
            Optional<Negocio> negocioOptional = negocioRepo.findByCodigo(codigoNegocio);
            if (!negocioOptional.get().getEstadoNegocio().equals(EstadoNegocio.PENDIENTE) || !negocioOptional.isPresent()) {
                throw new ResourceNotFoundException("No existe el negocio solicitado como " + EstadoNegocio.PENDIENTE.name());
            }
            Negocio negocio = negocioOptional.get();
            return negocio;
        } catch (RuntimeException ex) {
            throw new RuntimeException("codigo negocio no es válido o no corresponde", ex);
        }
    }

    /*Metodo que valida la existencia de un negocio que tenga como estadoNegocio igual a rechazado*/
    public Negocio validarNegocioRechazado(String codigoNegocio) throws Exception {

        try {
            Optional<Negocio> negocioOptional = negocioRepo.findByCodigo(codigoNegocio);
            if (!negocioOptional.get().getEstadoNegocio().equals(EstadoNegocio.RECHAZADO) || !negocioOptional.isPresent()) {
                throw new ResourceNotFoundException("No existe el negocio solicitado como " + EstadoNegocio.RECHAZADO.name());
            }
            Negocio negocio = negocioOptional.get();
            return negocio;
        } catch (RuntimeException ex) {
            throw new RuntimeException("codigo negocio no es válido o no corresponde", ex);
        }
    }

    public Negocio validarNegocioEliminado(String codigoNegocio) throws Exception {

        try {
            Optional<Negocio> negocioOptional = negocioRepo.findByCodigo(codigoNegocio);
            if (!negocioOptional.get().getEstadoNegocio().equals(EstadoNegocio.ELIMINADO) || !negocioOptional.isPresent()) {
                throw new ResourceNotFoundException("No existe el negocio solicitado como " + EstadoNegocio.ELIMINADO.name());
            }
            Negocio negocio = negocioOptional.get();
            return negocio;
        } catch (RuntimeException ex) {
            throw new RuntimeException("codigo negocio no es válido o no corresponde", ex);
        }
    }

    public List<HistorialRevision> validarListaHistorialRevision(String codigoNegocio) throws Exception {

        Negocio negocio = validarNegocioRechazado(codigoNegocio);
        if (negocio.getHistorialRevisiones().isEmpty()) {
            throw new ResourceNotFoundException("No tiene revisiones procesadas");
        }
        return negocio.getHistorialRevisiones();
    }

    public HistorialRevision buscarRevision(String codigoNegocio, String fecha) throws Exception {

        try {
            List<HistorialRevision> lista = validarListaHistorialRevision(codigoNegocio);
            HistorialRevision revision = null;
            for (HistorialRevision hr : lista) {
                if (hr.getFecha().equals(fecha)) {
                    revision = hr;
                }
            }
            if (revision == null) {
                throw new ResourceNotFoundException("Fecha no válida o no se encuentra");
            }
            return revision;
        } catch (RuntimeException ex) {
            throw new RuntimeException("Error, No se pudo hallar el recurso");
        }
    }

    public List<Negocio> validarListaGenericaNegocios(EstadoNegocio estado) throws Exception {

        List<Negocio> listaNegocios = negocioRepo.findAllByEstadoNegocio(estado);
        if (listaNegocios.isEmpty()) {
            throw new ResourceNotFoundException("No hay negocios con estado " + estado + " para mostrar");
        }
        return listaNegocios;
    }

    /*Metodo para validar la calificación que se da a un negocio (1 a 5) */
    public void validarCalificacionNegocio(ValorCalificar calificacion) throws Exception {

        if (calificacion.ordinal() < -1 || calificacion.ordinal() > 5) {
            throw new Exception("Valor no válido para calificar sitio");
        }
    }

    public Negocio validarNegocioPorNombre(String nombreNegocio) throws Exception {

        Optional<Negocio> negocioOptional = negocioRepo.findByNombre(nombreNegocio);
        if (negocioOptional == null) {
            throw new ResourceNotFoundException("Nombre no válido o no existe un negocio con el nombre " + nombreNegocio);
        }
        Negocio negocio = negocioOptional.get();
        return negocio;
    }

    public List<Negocio> validarListaNegociosPorTipo(TipoNegocio tipoNegocio) throws Exception {

        List<Negocio> listaNegocios = negocioRepo.findAllByEstadoNegocio(EstadoNegocio.APROBADO);
        List<Negocio> listaNegociosTipo = new ArrayList<>();
        for (Negocio n : listaNegocios) {
            if (n.getTipoNegocios().equals(tipoNegocio.name())) {
                listaNegociosTipo.add(n);
            }
        }
        if (listaNegociosTipo.size() == 0) {
            throw new ResourceNotFoundException("No hay negocios registrados de ese tipo");
        }
        return listaNegociosTipo;
    }
}
