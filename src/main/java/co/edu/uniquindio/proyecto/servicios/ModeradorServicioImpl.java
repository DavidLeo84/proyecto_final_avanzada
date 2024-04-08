package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.*;
import co.edu.uniquindio.proyecto.servicios.interfaces.IModeradorServicio;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ModeradorServicioImpl implements IModeradorServicio {

    private final ValidacionNegocio validacionNegocio;
    private final ValidacionCliente validacionCliente;
    private final ValidacionModerador validacionModerador;
    private final NegocioRepo negocioRepo;
    private final ModeradorRepo moderadorRepo;
    private final NegocioServicioImpl negocioServicio;
    private final EmailServicioImpl emailServicio;
    private final ClienteServicioImpl clienteServicio;

    @Override
    public void iniciarSesion(SesionDTO sesionDTO) throws Exception {

    }

    @Override
    public void eliminarCuenta(String codigoModerador) throws Exception {
        clienteServicio.eliminarCuenta(codigoModerador);
    }

    @Override
    public void enviarLinkRecuperacion(String destinatario) throws Exception {

        String email = validacionModerador.existeEmail(destinatario);;
        emailServicio.enviarEmail(email, "Recuperar contraseña",
                "http://localhost:8080/api/moderador/recoPass");
    }

    @Override
    public void cambiarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception {

    }

    @Override
    public void revisarNegocio(HistorialRevisionDTO revisionDTO) throws Exception {
        try {
            Negocio negocio = validacionModerador.buscarNegocioPendiente(revisionDTO.codigoNegocio());
            Cliente cliente = validacionCliente.buscarCliente(negocio.getCodigoCliente());
            if (validacionNegocio.validarCoordenadas(negocio.getUbicacion())) {
                HistorialRevision revision = HistorialRevision.builder()
                        .descripcion("La ubicación propuesta coincide con un establecimiento ya vigente")
                        .estadoNegocio(EstadoNegocio.RECHAZADO).fecha(validacionModerador.formatearFecha(LocalDateTime.now()))
                        .codigoModerador(revisionDTO.codigoModerador()).codigoNegocio(revisionDTO.codigoNegocio()).build();
                negocio.setEstadoNegocio(EstadoNegocio.RECHAZADO);
                negocio.getHistorialRevisiones().add(revision);
                negocioRepo.save(negocio);
                enviarEmail(EstadoNegocio.RECHAZADO.name(), negocio.getCodigoCliente());
            } else {
                HistorialRevision revision = HistorialRevision.builder()
                        .descripcion(revisionDTO.descripcion()).estadoNegocio(revisionDTO.estadoNegocio())
                        .fecha(validacionModerador.formatearFecha(LocalDateTime.now())).codigoModerador(revisionDTO.codigoModerador())
                        .codigoNegocio(revisionDTO.codigoNegocio()).build();
                switch (revisionDTO.estadoNegocio()) {
                    case APROBADO:
                        negocio.setEstadoNegocio(EstadoNegocio.APROBADO);
                        break;
                    case PENDIENTE:
                        negocio.setEstadoNegocio(EstadoNegocio.PENDIENTE);
                        break;
                    case RECHAZADO:
                        negocio.setEstadoNegocio(EstadoNegocio.RECHAZADO);
                        break;
                    default:
                        negocio.setEstadoNegocio(EstadoNegocio.ELIMINADO);
                        break;
                }
                negocio.getHistorialRevisiones().add(revision);
                negocioRepo.save(negocio);
                enviarEmail(revisionDTO.estadoNegocio().name(), negocio.getCodigoCliente());
            }
        } catch (
                IncorrectResultSizeDataAccessException e) {
            new IncorrectResultSizeDataAccessException("ubicacion invalida");
        }
    }

    private List<Negocio> eliminarNegocioCaducado(List<Negocio> rechazados) throws Exception {

        List<Negocio> actualizados = new ArrayList<>();
        try {
            LocalDateTime fechaActual = LocalDateTime.now();
            for (Negocio n : rechazados) {
                List<HistorialRevision> lista = validacionNegocio.validarListaHistorialRevision(n.getCodigo());
                HistorialRevision masReciente = lista.get(0);
                for (HistorialRevision hr : lista) {
                    LocalDateTime fecha1 = transformarFecha(masReciente.getFecha());
                    LocalDateTime fecha2 = transformarFecha(hr.getFecha());
                    if (fecha2.isAfter(fecha1)) {
                        masReciente = hr;
                    }
                }
                if (transformarFecha(masReciente.getFecha()).plusDays(5).isBefore(fechaActual)) {
                    n.setEstadoNegocio(EstadoNegocio.ELIMINADO);
                    negocioRepo.save(n);
                }
                actualizados.add(n);
            }
        } catch (Exception ex) {
            throw new Exception("Error al eliminar por caducidad el negocio rechazado");
        }
        return actualizados;
    }

    @Override
    public DetalleNegocioDTO obtenerNegocioAprobado(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioAprobado(negocioDTO.codigo());
        return new DetalleNegocioDTO(
                negocio.getNombre(),
                negocio.getTipoNegocios(),
                negocio.getUbicacion(),
                negocio.getDescripcion(),
                negocio.getHorarios(),
                negocio.getTelefonos(),
                negocio.getImagenes()
        );
    }

    @Override
    public DetalleNegocioDTO obtenerNegocioPendiente(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioPendiente(negocioDTO.codigo());
        return new DetalleNegocioDTO(
                negocio.getNombre(),
                negocio.getTipoNegocios(),
                negocio.getUbicacion(),
                negocio.getDescripcion(),
                negocio.getHorarios(),
                negocio.getTelefonos(),
                negocio.getImagenes()
        );
    }

    /*Método para buscar un negocio que fue revisado por el moderador y fue rechazado*/
    @Override
    public DetalleNegocioDTO obtenerNegocioRechazado(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioRechazado(negocioDTO.codigo());
        return new DetalleNegocioDTO(
                negocio.getNombre(),
                negocio.getTipoNegocios(),
                negocio.getUbicacion(),
                negocio.getDescripcion(),
                negocio.getHorarios(),
                negocio.getTelefonos(),
                negocio.getImagenes()
        );
    }

    @Override
    public DetalleNegocioDTO obtenerNegocioEliminado(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioEliminado(negocioDTO.codigo());
        return new DetalleNegocioDTO(
                negocio.getNombre(),
                negocio.getTipoNegocios(),
                negocio.getUbicacion(),
                negocio.getDescripcion(),
                negocio.getHorarios(),
                negocio.getTelefonos(),
                negocio.getImagenes()
        );
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosAprobados() throws Exception {
        List<Negocio> aprobados = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.APROBADO);
        return aprobados.stream()
                .map(n -> new ItemNegocioDTO(n.getCodigo(), n.getNombre(), n.getTipoNegocios()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosPendientes() throws Exception {
        List<Negocio> pendientes = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.PENDIENTE);
        return pendientes.stream()
                .map(n -> new ItemNegocioDTO(n.getCodigo(), n.getNombre(), n.getTipoNegocios()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosRechazados() throws Exception {

        List<Negocio> rechazados = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.RECHAZADO);
        List<Negocio> lista = eliminarNegocioCaducado(rechazados);
        List<Negocio> actualizados = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.RECHAZADO);
        return actualizados.stream()
                .map(n -> new ItemNegocioDTO(n.getCodigo(), n.getNombre(), n.getTipoNegocios()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosEliminados() throws Exception {
        List<Negocio> eliminados = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.ELIMINADO);
        return eliminados.stream()
                .map(n -> new ItemNegocioDTO(n.getCodigo(), n.getNombre(), n.getTipoNegocios()))
                .collect(Collectors.toList());
    }

    private void enviarEmail(String estado, String codigo) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigo);
        emailServicio.enviarEmail(cliente.getEmail(), "Respuesta solicitud de negocio", "Su negocio fue " + estado);
    }

    private LocalDateTime transformarFecha(String fechaRevision) throws Exception {

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
