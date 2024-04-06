package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
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

        String email = validacionModerador.existeEmail(destinatario);
        //Optional<Moderador> moderadorOptional = moderadorRepo.findByEmail(destinatario);
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
                        .descripcion("La ubicación propuesta coincide con un establecimiento presente")
                        .estadoNegocio(EstadoNegocio.RECHAZADO.name()).fecha(validacionModerador.formatearFecha(LocalDateTime.now()))
                        .codigoModerador(revisionDTO.codigoModerador()).codigoNegocio(revisionDTO.codigoNegocio()).build();
                negocio.setEstadoRegistro(EstadoRegistro.INACTIVO);
                negocio.getHistorialRevisiones().add(revision);
                negocioRepo.save(negocio);
                enviarEmail("RECHAZADO", negocio.getCodigoCliente());
            } else {
                HistorialRevision revision = HistorialRevision.builder()
                        .descripcion(revisionDTO.descripcion()).estadoNegocio(revisionDTO.estadoNegocio().name())
                        .fecha(validacionModerador.formatearFecha(LocalDateTime.now())).codigoModerador(revisionDTO.codigoModerador())
                        .codigoNegocio(revisionDTO.codigoNegocio()).build();
                negocio.getHistorialRevisiones().add(revision);
                switch (revisionDTO.estadoNegocio()) {
                    case PENDIENTE:
                        negocio.setEstadoRegistro(EstadoRegistro.INACTIVO);
                        break;
                    case APROBADO:
                        negocio.setEstadoRegistro(EstadoRegistro.ACTIVO);
                        break;
                    case ELIMINADO:
                        negocio.setEstadoRegistro(EstadoRegistro.ELIMINADO);
                        break;
                    default:
                        negocio.setEstadoRegistro(EstadoRegistro.INACTIVO);
                        break;
                }
                negocioRepo.save(negocio);
                enviarEmail(revisionDTO.estadoNegocio().name(), negocio.getCodigoCliente());
            }
        } catch (
                IncorrectResultSizeDataAccessException e) {
            new IncorrectResultSizeDataAccessException("ubicacion invalida");
        }
    }

    @Override
    public void eliminarNegocioCaducado(String codigo) throws Exception {

    }

    /* Método para buscar un negocio pendiente para revisar por primera vez por el moderador*/
    @Override
    public Negocio obtenerNegocioAprobado(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioAprobado(negocioDTO.codigo());
        return negocio;
    }

    /* Método para buscar un negocio pendiente para revisar por primera vez por el moderador*/
    @Override
    public Negocio obtenerNegocioPendiente(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioPendiente(negocioDTO.codigo());
        return negocio;
    }

    /*Método para buscar un negocio que fue revisado por el moderador y fue rechazado*/
    @Override
    public Negocio obtenerNegocioRechazado(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioRechazado(negocioDTO.codigo());
        return negocio;
    }

    @Override
    public Negocio obtenerNegocioEliminado(ItemNegocioDTO negocioDTO) throws Exception {
        Negocio negocio = validacionNegocio.validarNegocioEliminado(negocioDTO.codigo());
        return negocio;

    }

    @Override
    public List<ItemNegocioDTO> listarNegociosAprobados() throws Exception {
        List<Negocio> aprobados = validacionNegocio.validarListaNegociosPorActivo(EstadoRegistro.ACTIVO);
        return aprobados.stream()
                .map(n -> new ItemNegocioDTO(n.getCodigo(), n.getNombre(), n.getTipoNegocio()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosPendientes() throws Exception {
        List<Negocio> pendientes = validacionNegocio.validarListaNegociosPorInactivo(EstadoNegocio.PENDIENTE.name());
        return pendientes.stream()
                .map(n -> new ItemNegocioDTO(n.getCodigo(), n.getNombre(), n.getTipoNegocio()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosRechazados() throws Exception {
        List<Negocio> rechazados = validacionNegocio.validarListaNegociosPorInactivo(EstadoNegocio.RECHAZADO.name());
        return rechazados.stream()
                .map(n -> new ItemNegocioDTO(n.getCodigo(), n.getNombre(), n.getTipoNegocio()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosEliminados() throws Exception {
        List<Negocio> eliminados = validacionNegocio.validarListaNegociosPorEliminado(EstadoNegocio.ELIMINADO.name());
        return eliminados.stream()
                .map(n -> new ItemNegocioDTO(n.getCodigo(), n.getNombre(), n.getTipoNegocio()))
                .collect(Collectors.toList());
    }

    private void enviarEmail(String estado, String codigo) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigo);
        emailServicio.enviarEmail(cliente.getEmail(), "Respuesta solicitud de negocio", "Su negocio fue " + estado);
    }
}
