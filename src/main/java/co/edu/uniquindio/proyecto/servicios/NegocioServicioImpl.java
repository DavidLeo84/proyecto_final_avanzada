package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.enums.ValorCalificar;
import co.edu.uniquindio.proyecto.modelo.Horario;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.*;
import co.edu.uniquindio.proyecto.servicios.interfaces.INegocioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class NegocioServicioImpl implements INegocioServicio {

    private final ValidacionNegocio validacionNegocio;
    private final ValidacionCliente validacionCliente;
    private final ValidacionModerador validacionModerador;
    private final NegocioRepo negocioRepo;
    private final ClienteRepo clienteRepo;
    private Set<DetalleNegocioDTO> listaNegocios = new HashSet<>();
    private DetalleNegocioDTO negocioDTO = null;

    @Override
    public Negocio crearNegocio(RegistroNegocioDTO negocioDTO) throws Exception {

        validacionNegocio.existeCoordenadas(negocioDTO.ubicacion().getLongitud(), negocioDTO.ubicacion().getLatitud());
        Cliente cliente = validacionCliente.buscarCliente(negocioDTO.codigoCliente());
        Negocio nuevo = Negocio.builder().estadoNegocio(EstadoNegocio.PENDIENTE).ubicacion(negocioDTO.ubicacion())
                .codigoCliente(cliente.getCodigo()).nombre(negocioDTO.nombre())
                .descripcion(negocioDTO.descripcion()).tipoNegocios(new ArrayList<>(negocioDTO.tipoNegocios()))
                .horarios(negocioDTO.horarios()).telefonos(negocioDTO.telefonos())
                .imagenes(negocioDTO.imagenes()).calificaciones(new ArrayList<String>())
                .historialRevisiones(new ArrayList<HistorialRevision>()).recomendaciones(new ArrayList<>())
                .build();
        nuevo.getHistorialRevisiones().add(new HistorialRevision(
                "", EstadoNegocio.PENDIENTE, validacionModerador.formatearFecha(LocalDateTime.now()),
                "default", ""));
        negocioRepo.save(nuevo);
        cliente.getNegocios().add(nuevo.getCodigo());
        clienteRepo.save(cliente);
        return nuevo;
    }

    @Override
    public void actualizarNegocio(ActualizarNegocioDTO negocioDTO, String codigoNegocio) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        negocio.setDescripcion(negocioDTO.descripcion());
        negocio.setUbicacion(negocioDTO.ubicacion());
        negocio.setHorarios(negocioDTO.horarios());
        negocio.setTelefonos(negocioDTO.telefonos());
        negocio.setImagenes(negocioDTO.imagenes());
        negocioRepo.save(negocio);
    }

    @Override
    public void eliminarNegocio(String codigoNegocio) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        negocio.setEstadoNegocio(EstadoNegocio.ELIMINADO);
        negocioRepo.save(negocio);
    }

    @Override
    public DetalleNegocioDTO obtenerNegocio(String codigoNegocio) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        return new DetalleNegocioDTO(
                negocio.getNombre(), negocio.getTipoNegocios(),
                negocio.getUbicacion(), negocio.getDescripcion(),
                negocio.getHorarios(), negocio.getTelefonos(), negocio.getImagenes()
        );
    }

    @Override
    public List<ItemNegocioDTO> filtrarPorEstado(EstadoNegocio estado) throws Exception {

        validacionNegocio.validarEstadoListaNegocios(estado);
        List<Negocio> negocios = negocioRepo.findAllByEstadoNegocio(estado);
        List<DetalleNegocioDTO> negocioDTOList = new ArrayList<>();
        return negocios.stream()
                .map(n -> new ItemNegocioDTO(n.getCodigo(), n.getNombre(), n.getTipoNegocios())).collect(Collectors.toList());
    }

    @Override
    public Set<ItemNegocioDTO> listarNegociosPropietario(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        validacionCliente.listarNegociosCliente(codigoCliente);
        Set<Negocio> negocios = negocioRepo.findAllByCodigoCliente(codigoCliente);
        Set<ItemNegocioDTO> lista = new HashSet<>();
        return negocios.stream().map(n -> new ItemNegocioDTO(
                n.getCodigo(), n.getNombre(), n.getTipoNegocios())
        ).collect(Collectors.toSet());
    }

    /*@Override
    public void cambiarEstado(String codigoNegocio, EstadoNegocio estado) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        negocio.setEstadoNegocio(estado);
        negocioRepo.save(negocio);
    }*/

    @Override
    public void guardarRecomendados(String codigoNegocio, String codigoCliente) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        negocio.getRecomendaciones().add(1);
        cliente.getRecomendados().add(codigoNegocio);
        negocioRepo.save(negocio);
        clienteRepo.save(cliente);
    }

    //Metodo para visualizar un negocio recomendado por parte del cliente que lo recomendó
    @Override
    public DetalleNegocioDTO obtenerRecomendado(String codigoCliente, String codigoNegocio) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        Set<String> lista = validacionCliente.validarListaGenericaCliente(cliente.getRecomendados());
        for (String s : lista) {
            Negocio negocio = validacionNegocio.buscarNegocio(s);
            negocioDTO = new DetalleNegocioDTO(
                    negocio.getNombre(),
                    negocio.getTipoNegocios(),
                    negocio.getUbicacion(),
                    negocio.getDescripcion(),
                    negocio.getHorarios(),
                    negocio.getTelefonos(),
                    negocio.getImagenes());
        }
        return negocioDTO;
    }

    @Override
    public String eliminarNegocioRecomendado(String codigoNegocio, String codigoCliente) throws Exception {

        String respuesta = "";
        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        for (String codigo : cliente.getRecomendados()) {
            if (codigo.equals(codigoNegocio)) {
                cliente.getRecomendados().remove(codigoNegocio);
                clienteRepo.save(cliente);
                respuesta = "El negocio fue eliminado de su lista de recomendados con éxito";
            }
        }
        return respuesta;
    }

    //Metodo para listar los negocios recomendados del cliente
    @Override
    public Set<ItemNegocioDTO> listarRecomendadosCliente(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        Set<String> recomendados = validacionCliente.validarListaGenericaCliente(cliente.getRecomendados());
        Set<ItemNegocioDTO> lista = new HashSet<>();
        for (String s : recomendados) {
            Negocio negocio = validacionNegocio.buscarNegocio(s);
            lista.add(new ItemNegocioDTO(
                    negocio.getCodigo(),
                    negocio.getNombre(),
                    negocio.getTipoNegocios()));
        }
        return lista;
    }

    //Metodo para listar los negocios mas recomendados por los clientes
    public List<ItemNegocioDTO> listaNegociosRecomendadosPorClientes() throws Exception {

        List<Negocio> negocios = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.APROBADO);
        return negocios.stream().filter(n -> n.getRecomendaciones().size() > 4).map(n -> new ItemNegocioDTO(
                n.getCodigo(),
                n.getNombre(),
                n.getTipoNegocios())).collect(Collectors.toList());

    }

    @Override
    public DetalleRevisionDTO obtenerRevision(ItemRevisionDTO item) throws Exception {

        HistorialRevision revision = validacionNegocio.buscarRevision(item.codigoNegocio(), item.fecha());
        return new DetalleRevisionDTO(
                revision.getDescripcion(),
                revision.getEstadoNegocio(),
                revision.getFecha());
    }

    @Override
    public List<ItemRevisionDTO> listarRevisiones(String codigoNegocio) throws Exception {

        List<HistorialRevision> lista = validacionNegocio.validarListaHistorialRevision(codigoNegocio);
        return lista.stream().map(hr -> new ItemRevisionDTO(hr.getCodigoNegocio(), hr.getFecha())).collect(Collectors.toList());
    }

    @Override
    public void guardarNegocioFavorito(String codigoNegocio, String codigoCliente) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        Set<String> lista = cliente.getFavoritos();
        lista.add(negocio.getCodigo());
        clienteRepo.save(cliente);
    }

    @Override
    public String eliminarNegocioFavorito(String codigoNegocio, String codigoCliente) throws Exception {

        String respuesta = "";
        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
            for (String codigo : cliente.getFavoritos()) {
                if (codigo.equals(codigoNegocio)) {
                    cliente.getFavoritos().remove(codigo);
                    clienteRepo.save(cliente);
                    respuesta = "El negocio fue eliminado de su lista de favoritos con éxito";
                }
            }
            return respuesta;
    }

    @Override
    public Set<ItemNegocioDTO> listarFavoritos(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        Set<String> favoritos = validacionCliente.validarListaGenericaCliente(cliente.getFavoritos());
        Set<ItemNegocioDTO> lista = new HashSet<>();
        for (String s : favoritos) {
            Negocio negocio = validacionNegocio.buscarNegocio(s);
            lista.add(new ItemNegocioDTO(
                    negocio.getCodigo(),
                    negocio.getNombre(),
                    negocio.getTipoNegocios()));
        }
        return lista;
    }

    @Override
    public DetalleNegocioDTO obtenerFavorito(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        Set<String> favoritos = validacionCliente.validarListaGenericaCliente(cliente.getFavoritos());
        for (String s : favoritos) {
            Negocio negocio = validacionNegocio.buscarNegocio(s);
            negocioDTO = new DetalleNegocioDTO(
                    negocio.getNombre(),
                    negocio.getTipoNegocios(),
                    negocio.getUbicacion(),
                    negocio.getDescripcion(),
                    negocio.getHorarios(),
                    negocio.getTelefonos(),
                    negocio.getImagenes());
        }
        return negocioDTO;
    }

    @Override
    public void calificarNegocio(String codigoNegocio, ValorCalificar calificacion) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        validacionNegocio.validarCalificacionNegocio(calificacion);
        negocio.getCalificaciones().add(calificacion.name());
        negocioRepo.save(negocio);
    }

    @Override
    public float calcularPromedioCalificaficaciones(String codigoNegocio) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        int contador = negocio.getCalificaciones().stream()
                .mapToInt(calfn -> {
                    switch (calfn) {
                        case "ONE_STAR":
                            return 1;
                        case "TWO_STAR":
                            return 2;
                        case "THREE_STAR":
                            return 3;
                        case "FOUR_STAR":
                            return 4;
                        case "FIVE_STAR":
                            return 5;
                        default:
                            return 0;
                    }
                })
                .sum();

        float resultado = contador / negocio.getCalificaciones().size();
        return resultado;
    }

    @Override
    public String determinarDisponibilidadNegocio(String codigoNegocio, FechaActualDTO fechaActualDTO) throws Exception {

        String estado = "";
        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        for (Horario diaNegocio : negocio.getHorarios()) {
            if (diaNegocio.getDia().equals(fechaActualDTO.dia())) {
                for (Horario horaNegocio : negocio.getHorarios()) {
                    if (fechaActualDTO.hora().isBefore(transformarHora(horaNegocio.getHoraFin())) &&
                            fechaActualDTO.hora().isAfter(transformarHora(horaNegocio.getHoraInicio()))) {
                        estado = "Abierto";
                    } else {
                        estado = "Cerrado";
                    }
                }
            } else {
                throw new ResourceNotFoundException("No se puede validar el día");
            }
        }
        return estado;
    }

    @Override
    public DetalleNegocioDTO buscarNegocioPorNombre(String nombreNegocio) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioPorNombre(nombreNegocio);
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

    private LocalTime transformarHora(String hora) throws Exception {

        try {
            DateTimeFormatter formatohora = DateTimeFormatter.ISO_TIME.withLocale(Locale.ENGLISH);
            TemporalAccessor horaFormateada = formatohora.parse(hora);
            return LocalTime.from(horaFormateada);
        } catch (Exception ex) {
            throw new Exception("La hora no cumple con el formato requerido");
        }
    }
}

