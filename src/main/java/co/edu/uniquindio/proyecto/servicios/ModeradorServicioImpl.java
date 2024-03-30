package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.CambioPasswordDTO;
import co.edu.uniquindio.proyecto.dtos.HistorialRevisionDTO;
import co.edu.uniquindio.proyecto.dtos.SesionDTO;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionNegocio;
import co.edu.uniquindio.proyecto.servicios.interfaces.IEmailServicio;
import co.edu.uniquindio.proyecto.servicios.interfaces.IModeradorServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ModeradorServicioImpl implements IModeradorServicio {

    private final ValidacionNegocio validacion;
    private final ValidacionCliente validacionCliente;
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
//        clienteServicio.eliminarCuenta(codigoModerador);
    }

    @Override
    public void enviarLinkRecuperacion(String destinatario) throws Exception {

//        validacionModerador.existeEmail(destinatario);
//        //Optional<Moderador> moderadorOptional = moderadorRepo.findByEmail(destinatario);
//        emailServicio.enviarEmail(destinatario, "Recuperar contraseña",
//                "http://localhost:8080/api/moderador/recoPass");
    }

    @Override
    public void cambiarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception {

    }

    @Override
    public HistorialRevision revisarNegocio(HistorialRevisionDTO revisionDTO) throws Exception {

        Negocio negocio = validacion.buscarNegocioPendiente(revisionDTO.codigoNegocio());
        Cliente cliente = validacionCliente.buscarCliente(negocio.getCodigoCliente());

        HistorialRevision revision = HistorialRevision.builder()
                .descripcion(revisionDTO.descripcion())
                .estadoNegocio(revisionDTO.estadoNegocio().name())
                .fecha(formatearFecha(revisionDTO.fecha()))
                .codigoModerador(revisionDTO.codigoModerador())
                .codigoNegocio(revisionDTO.codigoNegocio())
                .build();
        if (revisionDTO.estadoNegocio().equals(EstadoNegocio.PENDIENTE)) {
            negocio.setEstadoRegistro(EstadoRegistro.INACTIVO);
        }
        if (revisionDTO.estadoNegocio().equals(EstadoNegocio.APROBADO)) {
            negocio.setEstadoRegistro(EstadoRegistro.ACTIVO);
        }

        if (revisionDTO.estadoNegocio().equals(EstadoNegocio.RECHAZADO)) {
            negocio.setEstadoRegistro(EstadoRegistro.INACTIVO);
        }
        if (revisionDTO.estadoNegocio().equals(EstadoNegocio.ELIMINADO)) {
            negocio.setEstadoRegistro(EstadoRegistro.INACTIVO);
        }
        negocio.getHistorialRevisiones().add(revision);
        negocioRepo.save(negocio);
        enviarEmail(revisionDTO, negocio.getCodigoCliente());
        return revision;
    }

    @Override
    public void eliminarNegocioCaducado(String codigo) throws Exception {

    }


    private void enviarEmail(HistorialRevisionDTO revision, String codigo) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigo);
        emailServicio.enviarEmail(cliente.getEmail(), revision.descripcion(), "Su negocio fue " + revision.estadoNegocio());
    }

    private String formatearFecha(LocalDateTime localDateTime) {

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss a", Locale.ENGLISH);
        LocalDateTime revisionFecha = localDateTime;
        String fechaRevision = formatoFecha.format(revisionFecha);
        return fechaRevision;
    }
}
