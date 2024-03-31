package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.CambioPasswordDTO;
import co.edu.uniquindio.proyecto.dtos.DetalleNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.HistorialRevisionDTO;
import co.edu.uniquindio.proyecto.dtos.SesionDTO;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.IncorrectResultSizeDataAccessException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionModerador;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionNegocio;
import co.edu.uniquindio.proyecto.servicios.interfaces.IModeradorServicio;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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
        /*clienteServicio.eliminarCuenta(codigoModerador);*/
    }

    @Override
    public void enviarLinkRecuperacion(String destinatario) throws Exception {

       /* String email = validacionModerador.existeEmail(destinatario);
        //Optional<Moderador> moderadorOptional = moderadorRepo.findByEmail(destinatario);
        emailServicio.enviarEmail(email, "Recuperar contraseña",
                "http://localhost:8080/api/moderador/recoPass");*/
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
                        .estadoNegocio(EstadoNegocio.RECHAZADO.name()).fecha(formatearFecha(LocalDateTime.now()))
                        .codigoModerador(revisionDTO.codigoModerador()).codigoNegocio(revisionDTO.codigoNegocio())
                        .build();

                negocio.setEstadoRegistro(EstadoRegistro.INACTIVO);
                negocio.getHistorialRevisiones().add(revision);
                negocioRepo.save(negocio);
                enviarEmail("RECHAZADO", negocio.getCodigoCliente());

            } else {

                HistorialRevision revision = HistorialRevision.builder()
                        .descripcion(revisionDTO.descripcion()).estadoNegocio(revisionDTO.estadoNegocio().name())
                        .fecha(formatearFecha(LocalDateTime.now())).codigoModerador(revisionDTO.codigoModerador())
                        .codigoNegocio(revisionDTO.codigoNegocio()).build();

                negocio.getHistorialRevisiones().add(revision);

                if ((revisionDTO.estadoNegocio().equals(EstadoNegocio.PENDIENTE))) {
                    negocio.setEstadoRegistro(EstadoRegistro.ACTIVO);
                }

                if (revisionDTO.estadoNegocio().equals(EstadoNegocio.APROBADO)) {
                    negocio.setEstadoRegistro(EstadoRegistro.ACTIVO);
                }

                if (revisionDTO.estadoNegocio().equals(EstadoNegocio.RECHAZADO)) {
                    negocio.setEstadoRegistro(EstadoRegistro.INACTIVO);
                }
                if (revisionDTO.estadoNegocio().equals(EstadoNegocio.ELIMINADO)) {
                    negocio.setEstadoRegistro(EstadoRegistro.ELIMINADO);
                }
                negocioRepo.save(negocio);
                enviarEmail(revisionDTO.estadoNegocio().name(), negocio.getCodigoCliente());
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            new IncorrectResultSizeDataAccessException("ubicacion invalida");
        }
    }

    @Override
    public void eliminarNegocioCaducado(String codigo) throws Exception {

    }

    @Override
    public void obtenerNegocioPendiente(String codigoNegocio) throws Exception {

    }

    @Override
    public List<DetalleNegocioDTO> listarNegociosPendientes() throws Exception {
        return null;
    }

    @Override
    public void obtenerNegocioRechazado(String codigoNegocio) throws Exception {

    }

    @Override
    public List<DetalleNegocioDTO> listarNegociosRechazados() throws Exception {
        return null;
    }

    @Override
    public void obtenerNegocioEliminado(String codigoNegocio) throws Exception {

    }

    @Override
    public List<DetalleNegocioDTO> listarNegociosEliminados() throws Exception {
        return null;
    }


    private void enviarEmail(String estado, String codigo) throws Exception {


        Cliente cliente = validacionCliente.buscarCliente(codigo);
        emailServicio.enviarEmail(cliente.getEmail(), "Respuesta solicitud de negocio", "Su negocio fue " + estado);
    }

    private String formatearFecha(LocalDateTime localDateTime) {

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss a", Locale.ENGLISH);
        LocalDateTime revisionFecha = localDateTime;
        String fechaRevision = formatoFecha.format(revisionFecha);
        return fechaRevision;
    }
}
