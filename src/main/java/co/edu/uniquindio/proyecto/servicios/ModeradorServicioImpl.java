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
import java.util.NoSuchElementException;
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
    public String cambiarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception {

        Moderador moderador = validacionModerador.buscarModerador(cambioPasswordDTO.codigoUsuario());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(cambioPasswordDTO.passwordActual(), moderador.getPassword())) {
            throw new NoSuchElementException("La contraseña es incorrecta");
        }
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

            if (validacionNegocio.validarCoordenadas(negocio.getUbicacion(), negocio.getLocal())) {

                HistorialRevision revision2 = HistorialRevision.builder()
                        .descripcion("La ubicación propuesta coincide con un establecimiento ya vigente")
                        .estadoNegocio(EstadoNegocio.RECHAZADO.name()).fecha(validacionModerador.formatearFecha(LocalDateTime.now()))
                        .codigoModerador(revisionDTO.codigoModerador()).codigoNegocio(revisionDTO.codigoNegocio()).build();
                negocio.setEstadoNegocio(EstadoNegocio.RECHAZADO.name());
                negocio.getHistorialRevisiones().add(revision2);
                negocioRepo.save(negocio);
                enviarEmail(EstadoNegocio.RECHAZADO.name(), negocio.getCodigoCliente());
                throw new ResourceNotFoundException("Ya existe un negocio en el local");
            }
            HistorialRevision revision1 = HistorialRevision.builder()
                    .descripcion(revisionDTO.descripcion()).estadoNegocio(revisionDTO.estadoNegocio().name())
                    .fecha(validacionModerador.formatearFecha(LocalDateTime.now())).codigoModerador(revisionDTO.codigoModerador())
                    .codigoNegocio(revisionDTO.codigoNegocio()).build();

            negocio.setEstadoNegocio(revisionDTO.estadoNegocio().name());
            negocio.getHistorialRevisiones().add(revision1);
            negocioRepo.save(negocio);
            enviarEmail(negocio.getEstadoNegocio(), negocio.getCodigoCliente());


        } catch (
                IncorrectResultSizeDataAccessException e) {
            new IncorrectResultSizeDataAccessException("ubicacion invalida");
        }
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosRechazados() throws Exception {

        List<Negocio> rechazados = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.RECHAZADO);
        LocalDateTime fechaActual = LocalDateTime.now();
        for (Negocio n : rechazados) {
            List<HistorialRevision> lista = validacionNegocio.validarListaHistorialRevision(n.getCodigoNegocio());
            HistorialRevision masReciente = lista.get(0);
            for (HistorialRevision hr : lista) {
                LocalDateTime fecha1 = validacionModerador.transformarFecha(masReciente.getFecha());
                LocalDateTime fecha2 = validacionModerador.transformarFecha(hr.getFecha());
                if (fecha2.isAfter(fecha1)) {
                    masReciente = hr;
                }
            }
            if (validacionModerador.transformarFecha(masReciente.getFecha()).plusDays(5).isBefore(fechaActual)) {
                n.setEstadoNegocio(EstadoNegocio.ELIMINADO.name());
                negocioRepo.save(n);
            }
        }
        List<Negocio> actualizados = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.RECHAZADO);
        if (actualizados.size() == 0) {
            throw new Exception("No hay negocios con estado rechazados para mostrar");
        }
        return actualizados
                .stream()
                .map(n -> new ItemNegocioDTO(
                        n.getCodigoNegocio(),
                        n.getNombre(),
                        n.getTipoNegocios(),
                        n.getUbicacion(),
                        n.getImagenes()))
                .collect(Collectors.toList());
    }

    @Override
    public DetalleNegocioDTO obtenerNegocioAprobado(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioAprobado(negocioDTO.codigoNegocio());
        return new DetalleNegocioDTO(
                negocio.getCodigoNegocio(), negocio.getNombre(),
                negocio.getTipoNegocios(), negocio.getUbicacion(),
                negocio.getLocal(), negocio.getDescripcion(),
                negocio.getCalificacion(), negocio.getHorarios(),
                negocio.getTelefonos(), negocio.getImagenes());
    }

    @Override
    public DetalleNegocioDTO obtenerNegocioPendiente(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioPendiente(negocioDTO.codigoNegocio());
        return new DetalleNegocioDTO(
                negocio.getCodigoNegocio(), negocio.getNombre(),
                negocio.getTipoNegocios(), negocio.getUbicacion(),
                negocio.getLocal(), negocio.getDescripcion(),
                negocio.getCalificacion(), negocio.getHorarios(),
                negocio.getTelefonos(), negocio.getImagenes()
        );
    }

    /*Método para buscar un negocio que fue revisado por el moderador y fue rechazado*/
    @Override
    public DetalleNegocioDTO obtenerNegocioRechazado(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioRechazado(negocioDTO.codigoNegocio());
        return new DetalleNegocioDTO(
                negocio.getCodigoNegocio(), negocio.getNombre(),
                negocio.getTipoNegocios(), negocio.getUbicacion(),
                negocio.getLocal(), negocio.getDescripcion(),
                negocio.getCalificacion(), negocio.getHorarios(),
                negocio.getTelefonos(), negocio.getImagenes()
        );
    }

    @Override
    public DetalleNegocioDTO obtenerNegocioEliminado(ItemNegocioDTO negocioDTO) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioEliminado(negocioDTO.codigoNegocio());
        return new DetalleNegocioDTO(
                negocio.getCodigoNegocio(), negocio.getNombre(),
                negocio.getTipoNegocios(), negocio.getUbicacion(),
                negocio.getLocal(), negocio.getDescripcion(),
                negocio.getCalificacion(), negocio.getHorarios(),
                negocio.getTelefonos(), negocio.getImagenes());
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosAprobados() throws Exception {
        List<Negocio> aprobados = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.APROBADO);
        return aprobados
                .stream()
                .map(n -> new ItemNegocioDTO(
                        n.getCodigoNegocio(),
                        n.getNombre(),
                        n.getTipoNegocios(),
                        n.getUbicacion(),
                        n.getImagenes()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosPendientes() throws Exception {
        List<Negocio> pendientes = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.PENDIENTE);
        return pendientes
                .stream()
                .map(n -> new ItemNegocioDTO(
                        n.getCodigoNegocio(),
                        n.getNombre(),
                        n.getTipoNegocios(),
                        n.getUbicacion(),
                        n.getImagenes()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosEliminados() throws Exception {
        List<Negocio> eliminados = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.ELIMINADO);
        return eliminados
                .stream()
                .map(n -> new ItemNegocioDTO(
                        n.getCodigoNegocio(),
                        n.getNombre(),
                        n.getTipoNegocios(),
                        n.getUbicacion(),
                        n.getImagenes()))
                .collect(Collectors.toList());
    }



    private void enviarEmail(String estado, String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        emailServicio.enviarEmail(cliente.getEmail(), "Respuesta solicitud de negocio", "Su negocio fue " + estado);
    }
}
