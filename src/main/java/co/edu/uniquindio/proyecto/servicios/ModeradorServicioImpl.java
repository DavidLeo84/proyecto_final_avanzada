package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.*;
import co.edu.uniquindio.proyecto.servicios.interfaces.*;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ModeradorServicioImpl implements IModeradorServicio {

    private final ValidacionNegocio validacionNegocio;
    private final ValidacionCliente validacionCliente;
    private final ValidacionModerador validacionModerador;
    private final NegocioRepo negocioRepo;
    private final ModeradorRepo moderadorRepo;
    private final ClienteRepo clienteRepo;
    private final EmailServicioImpl emailServicio;
    private final NegocioServicioImpl negocioServicio;
    private final ClienteServicioImpl clienteServicio;
    private final AutenticacionServicioImpl autenticacionServicio;

    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception {

        TokenDTO token = autenticacionServicio.iniciarSesionModerador(loginDTO);
        return token;
    }

    @Override
    public void eliminarCuenta(String codigoModerador) throws Exception {
        try {
            Moderador moderador = validacionModerador.buscarModerador(codigoModerador);
            moderador.setEstadoRegistro(EstadoRegistro.INACTIVO);
            moderadorRepo.save(moderador);
        } catch (ResourceNotFoundException e) {
            e.getMessage();
        }
    }

    @Override
    public TokenDTO enviarLinkRecuperacion(String email) throws Exception {

        Optional<Cliente> clienteOptional = clienteRepo.findByEmail(email);
        Cliente cliente = null;
        if (clienteOptional.isEmpty()) {
            cliente = clienteOptional.get();
        }
        Optional<Moderador> moderadorOptional = moderadorRepo.findByEmail(email);
        Moderador moderador = null;

        if (moderadorOptional.isPresent()) {
            moderador = moderadorOptional.get();
        }
        if (!moderadorOptional.isPresent() && !clienteOptional.isPresent()) {
            throw new ResourceNotFoundException("El usuario no se encuentra registrado");
        }
        emailServicio.enviarEmail(email, "Recuperar contraseña",
                "http://localhost:8080/api/recoPass");
        TokenDTO token = autenticacionServicio.recuperarPasswordModerador(email);
        return token;
    }

    @Override
    public String cambiarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception {

        Moderador moderador = validacionModerador.buscarModerador(cambioPasswordDTO.codigoUsuario());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String nuevaPassword = passwordEncoder.encode(cambioPasswordDTO.passwordNueva());
        moderador.setPassword(nuevaPassword);
        moderadorRepo.save(moderador);
        return "El password fue cambiado con éxito";
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
                    LocalDateTime fecha1 = validacionModerador.transformarFecha(masReciente.getFecha());
                    LocalDateTime fecha2 = validacionModerador.transformarFecha(hr.getFecha());
                    if (fecha2.isAfter(fecha1)) {
                        masReciente = hr;
                    }
                }
                if (validacionModerador.transformarFecha(masReciente.getFecha()).plusDays(5).isBefore(fechaActual)) {
                    n.setEstadoNegocio(EstadoNegocio.ELIMINADO);
                    negocioRepo.save(n);
                }
                actualizados.add(n);
            }
            return actualizados;
        } catch (Exception ex) {
            throw new Exception("Error al eliminar por caducidad el negocio rechazado");
        }
    }

    @Override
    public DetalleNegocioDTO obtenerNegocioAprobado(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioAprobado(negocioDTO.codigo());
        return new DetalleNegocioDTO(
                negocio.getCodigo(),
                negocio.getNombre(),
                negocio.getTipoNegocios(),
                negocio.getUbicacion(),
                negocio.getDescripcion(),
                negocio.getCalificacion(),
                negocio.getHorarios(),
                negocio.getTelefonos(),
                negocio.getImagenes()
        );
    }

    @Override
    public DetalleNegocioDTO obtenerNegocioPendiente(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioPendiente(negocioDTO.codigo());
        return new DetalleNegocioDTO(
                negocio.getCodigo(),
                negocio.getNombre(),
                negocio.getTipoNegocios(),
                negocio.getUbicacion(),
                negocio.getDescripcion(),
                negocio.getCalificacion(),
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
                negocio.getCodigo(),
                negocio.getNombre(),
                negocio.getTipoNegocios(),
                negocio.getUbicacion(),
                negocio.getDescripcion(),
                negocio.getCalificacion(),
                negocio.getHorarios(),
                negocio.getTelefonos(),
                negocio.getImagenes()
        );
    }

    @Override
    public DetalleNegocioDTO obtenerNegocioEliminado(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioEliminado(negocioDTO.codigo());
        return new DetalleNegocioDTO(
                negocio.getCodigo(),
                negocio.getNombre(),
                negocio.getTipoNegocios(),
                negocio.getUbicacion(),
                negocio.getDescripcion(),
                negocio.getCalificacion(),
                negocio.getHorarios(),
                negocio.getTelefonos(),
                negocio.getImagenes()
        );
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosAprobados() throws Exception {
        List<Negocio> aprobados = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.APROBADO);
        return aprobados
                .stream()
                .map(n -> new ItemNegocioDTO(
                        n.getCodigo(),
                        n.getNombre(),
                        n.getTipoNegocios(),
                        n.getUbicacion()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosPendientes() throws Exception {
        List<Negocio> pendientes = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.PENDIENTE);
        return pendientes
                .stream()
                .map(n -> new ItemNegocioDTO(
                        n.getCodigo(),
                        n.getNombre(),
                        n.getTipoNegocios(),
                        n.getUbicacion()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosRechazados() throws Exception {

        List<Negocio> rechazados = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.RECHAZADO);
        List<Negocio> lista = eliminarNegocioCaducado(rechazados);
        List<Negocio> actualizados = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.RECHAZADO);
        return actualizados
                .stream()
                .map(n -> new ItemNegocioDTO(
                        n.getCodigo(),
                        n.getNombre(),
                        n.getTipoNegocios(),
                        n.getUbicacion()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosEliminados() throws Exception {
        List<Negocio> eliminados = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.ELIMINADO);
        return eliminados
                .stream()
                .map(n -> new ItemNegocioDTO(
                        n.getCodigo(),
                        n.getNombre(),
                        n.getTipoNegocios(),
                        n.getUbicacion()))
                .collect(Collectors.toList());
    }

    private void enviarEmail(String estado, String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        emailServicio.enviarEmail(cliente.getEmail(), "Respuesta solicitud de negocio", "Su negocio fue " + estado);
    }
}
