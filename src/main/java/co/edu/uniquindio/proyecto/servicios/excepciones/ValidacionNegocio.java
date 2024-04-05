package co.edu.uniquindio.proyecto.servicios.excepciones;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.ValorCalificar;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.Ubicacion;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public void existeCoordenadas(int latitud, int longitud) throws Exception {

        Optional<Negocio> buscado = negocioRepo.getNegocioByUbicacion(latitud, longitud);
        if (buscado.isPresent() && buscado.get().getEstadoRegistro().equals(EstadoRegistro.ACTIVO)) {
            throw new ResourceNotFoundException("Ya existe un local en las coordenadas seleccionadas");
        }
    }

    /*metodo para validar las coordenadas que se ingresaron y en la revision se debe de nuevo validar*/
    public boolean validarCoordenadas(Ubicacion ubicacion) throws Exception {

        List<Negocio> negocioOptional = negocioRepo.getListNegocioByUbicacion(ubicacion.getLatitud(), ubicacion.getLongitud());
        if (negocioOptional.size() > 1) {
            return true;
        }
        return false;
    }

    /*Metodo para obtener un negocio que tenga estadoRegistro igual a Activo */
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

    /*Metodo para validar si existe una lista de negocios con estado activo o inactivo*/
    public void validarEstadoListaNegocios(EstadoRegistro estadoRegistro) throws Exception {

        List<Negocio> lista = negocioRepo.findAllByEstadoRegistro(estadoRegistro);
        if (lista.isEmpty()) {
            throw new ResourceNotFoundException("No existen negocios con el estado " + estadoRegistro.name());
        }
    }

    /*Metodo que valida la existencia de un negocio que tenga como estadoNegocio aprobado*/
    public Negocio validarNegocioAprobado(String codigoNegocio) throws Exception {

        Negocio negocio = buscarNegocio(codigoNegocio);
        return negocio;
    }

    /*Metodo que valida la existencia de un negocio que tenga como estadoNegocio igual a pendiente*/
    public Negocio validarNegocioPendiente(String codigoNegocio) throws Exception {

        Optional<Negocio> negocioOptional = negocioRepo.findByCodigo(codigoNegocio);
        HistorialRevision revision = obtenerRevision(negocioOptional.get().getCodigo());
        if (!revision.getEstadoNegocio().equals(EstadoNegocio.PENDIENTE.name())) {
            throw new ResourceNotFoundException("El negocio no tiene revisiones procesadas rechazadas");
        }
        Negocio negocio = negocioOptional.get();
        return negocio;
    }

    /*Metodo que valida la existencia de un negocio que tenga como estadoNegocio igual a rechazado*/
    public Negocio validarNegocioRechazado(String codigoNegocio) throws Exception {

        Optional<Negocio> negocioOptional = negocioRepo.findByCodigo(codigoNegocio);
        HistorialRevision revision = obtenerRevision(negocioOptional.get().getCodigo());
        if (!revision.getEstadoNegocio().equals(EstadoNegocio.RECHAZADO.name())) {
            throw new ResourceNotFoundException("El negocio no tiene revisiones procesadas rechazadas");
        }
        Negocio negocio = negocioOptional.get();
        return negocio;
    }

    /*Metodo para obtener y/o validar el estado de una historiaRevision que esta en una lista en un negocio */
    public HistorialRevision obtenerRevision(String codigoNegocio) throws Exception {

        Optional<Negocio> negocioOptional = negocioRepo.findByCodigo(codigoNegocio);
        if (negocioOptional == null) {
            throw new ResourceNotFoundException("No existe el negocio pendiente con el codigo " + codigoNegocio);
        }
        if (negocioOptional.get().getHistorialRevisiones().isEmpty()) {
            throw new ResourceNotFoundException("No tiene revisiones procesadas");
        }
        HistorialRevision masReciente = negocioOptional.get().getHistorialRevisiones().get(0); // Empezar con el primer elemento
        for (HistorialRevision historial : negocioOptional.get().getHistorialRevisiones()) {
            try {
                String fechaString1 = masReciente.getFecha().replaceAll("/", "-");
                String fechaString3 = fechaString1.replaceAll(" ", "T");

                String fechaString2 = historial.getFecha().replaceAll("/", "-");
                String fechaString4 = fechaString2.replaceAll(" ", "T");
                DateTimeFormatter formatoFecha = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

                TemporalAccessor fechaFormateada1 = formatoFecha.parse(fechaString3.substring(0, 20));
                LocalDateTime fecha1 = LocalDateTime.from(fechaFormateada1);

                TemporalAccessor fechaFormateada2 = formatoFecha.parse(fechaString4.substring(0, 20));
                LocalDateTime fecha2 = LocalDateTime.from(fechaFormateada2);
                if (fecha2.isAfter(fecha1)) {
                    masReciente = historial;
                }
            } catch (DateTimeParseException ex) {
                throw new Exception("fecha no válida", ex);
            }
        }
        System.out.println("masReciente = " + masReciente.toString());
        return masReciente;
    }

    /*Metodo para validar y/o retornar un listado de negocios con estadoRegistro activo
     * y estadoNegocio igual a aprobado*/
    public List<Negocio> validarListaNegociosPorActivo(EstadoRegistro estado) throws Exception {

        List<Negocio> listaNegocios = negocioRepo.findAllByEstadoRegistro(estado);

        if (listaNegocios.isEmpty()) {
            throw new ResourceNotFoundException("No hay negocios APROBADOS para mostrar");
        }
        return listaNegocios;
    }

    /*Metodo para validar y/o retornar un listado de negocios con estadoRegistro igual a inactivo y
    estadoNegocio igual a Pendiente o rechazado*/
    public List<Negocio> validarListaNegociosPorInactivo(String estado) throws Exception {

        List<Negocio> listaNegocios = negocioRepo.getListNegociosInactivos(estado);
        if (listaNegocios.isEmpty()) {
            throw new ResourceNotFoundException("No hay negocios " + estado.concat("s") + " para mostrar");
        }
        List<Negocio> negocios = new ArrayList<>();
        for (Negocio n : listaNegocios) {
            HistorialRevision revision = obtenerRevision(n.getCodigo());
            /*if (revision.getEstadoNegocio().equals(EstadoNegocio.valueOf(estado))) {
                negocios.add(n);
                System.out.println("negocios = " + negocios.toString());
            }*/
            EstadoNegocio.valueOf(estado);
            negocios.add(n);
        }
        if (negocios.isEmpty()) {
            throw new ResourceNotFoundException("No hay negocios " + estado.concat("S") + " para mostrar");
        }
        return negocios;
    }

    /*Metodo para validar y/o retornar un listado de negocios con estadoRegistro igual a inactivo y
     * estadoNegocio igual a eliminado*/
    public List<Negocio> validarListaNegociosPorEliminado(String estado) throws Exception {

        List<Negocio> listaNegocios = negocioRepo.getListNegociosEliminados(estado);
        if (listaNegocios.isEmpty()) {
            throw new ResourceNotFoundException("No hay negocios " + estado.concat("s") + " para mostrar");
        }
        List<Negocio> negocios = new ArrayList<>();
        for (Negocio n : listaNegocios) {
            HistorialRevision revision = obtenerRevision(n.getCodigo());
            /*if (revision.getEstadoNegocio().equals(EstadoNegocio.valueOf(estado))) {
                negocios.add(n);
                System.out.println("negocios = " + negocios.toString());
            }*/
            EstadoNegocio.valueOf(estado);
            negocios.add(n);
        }
        if (negocios.isEmpty()) {
            throw new ResourceNotFoundException("No hay negocios " + estado.concat("S") + " para mostrar");
        }
        return negocios;
    }

    /*Metodo para validar y/o retornar un negocio con estadoNegocio igual a eliminado*/
    public Negocio validarNegocioEliminado(String codigoNegocio) throws Exception {

        Optional<Negocio> negocioOptional = negocioRepo.findByCodigo(codigoNegocio);
        HistorialRevision revision = obtenerRevision(negocioOptional.get().getCodigo());
        if (!revision.getEstadoNegocio().equals(EstadoNegocio.ELIMINADO.name())) {
            throw new ResourceNotFoundException("El negocio no tiene revisiones procesadas rechazadas");
        }
        Negocio negocio = negocioOptional.get();
        return negocio;
    }

    /*Metodo para validar la calificación que se da a un negocio (1 a 5) */
    public void validarCalificacionNegocio(ValorCalificar calificacion) throws Exception {

        if (calificacion.ordinal() < 0 || calificacion.ordinal() > 5) {
            throw new ResourceInvalidException("Valor no válido para calificar sitio");
        }
    }
}
