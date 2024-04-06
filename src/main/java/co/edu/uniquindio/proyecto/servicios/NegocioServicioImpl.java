package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.ActualizarNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.DetalleNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.ItemNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroNegocioDTO;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.enums.ValorCalificar;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceInvalidException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionModerador;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionNegocio;
import co.edu.uniquindio.proyecto.servicios.interfaces.INegocioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        Negocio nuevo = Negocio.builder().estadoRegistro(EstadoRegistro.INACTIVO).ubicacion(negocioDTO.ubicacion())
                .codigoCliente(cliente.getCodigo()).nombre(negocioDTO.nombre())
                .descripcion(negocioDTO.descripcion()).tipoNegocio(TipoNegocio.valueOf(negocioDTO.tipoNegocio()))
                .horarios(negocioDTO.horarios()).telefonos(negocioDTO.telefonos())
                .imagenes(negocioDTO.imagenes()).calificaciones(new ArrayList<String>())
                .historialRevisiones(new ArrayList<HistorialRevision>()).build();
        nuevo.getHistorialRevisiones().add(new HistorialRevision(
                "", EstadoNegocio.PENDIENTE.name(), validacionModerador.formatearFecha(LocalDateTime.now()),
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
        negocio.setHorarios(negocioDTO.horarios());
        negocio.setTelefonos(negocioDTO.telefonos());
        negocio.setImagenes(negocioDTO.imagenes());
        negocioRepo.save(negocio);
    }

    @Override
    public void eliminarNegocio(String codigoNegocio) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        negocio.setEstadoRegistro(EstadoRegistro.INACTIVO);
        negocioRepo.save(negocio);
    }

    @Override
    public DetalleNegocioDTO buscarNegocio(String codigoNegocio) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        return new DetalleNegocioDTO(
                negocio.getNombre(), negocio.getUbicacion(),
                negocio.getDescripcion(), negocio.getHorarios(),
                negocio.getTelefonos(), negocio.getImagenes()
        );
    }

    /*Metodo para generar una lista de negocios que tiene estado activo o inactivo*/
    @Override
    public List<ItemNegocioDTO> filtrarPorEstado(EstadoRegistro estadoRegistro) throws Exception {

        validacionNegocio.validarEstadoListaNegocios(estadoRegistro);
        List<Negocio> negocios = negocioRepo.findAllByEstadoRegistro(estadoRegistro);
        List<DetalleNegocioDTO> negocioDTOList = new ArrayList<>();
        return negocios.stream()
                .map(n -> new ItemNegocioDTO(n.getCodigo(), n.getNombre(), n.getTipoNegocio())).collect(Collectors.toList());
    }

    @Override
    public Set<ItemNegocioDTO> listarNegociosPropietario(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        validacionCliente.validarListaNegociosCliente(codigoCliente);
        Set<Negocio> negocios = negocioRepo.findAllByCodigoCliente(codigoCliente);
        Set<ItemNegocioDTO> lista = new HashSet<>();
        return negocios.stream().map(n -> new ItemNegocioDTO(
                n.getCodigo(), n.getNombre(), n.getTipoNegocio())
        ).collect(Collectors.toSet());
    }

    @Override
    public void cambiarEstado(String codigoNegocio, EstadoRegistro estadoRegistro) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        negocio.setEstadoRegistro(estadoRegistro);
        negocioRepo.save(negocio);
    }

    @Override
    public void guardarRecomendados(String codigoNegocio, String codigoCliente) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        cliente.getRecomendados().add(codigoNegocio);
        clienteRepo.save(cliente);
    }

    @Override
    public DetalleNegocioDTO obtenerRecomendado(String codigoCliente, String codigoNegocio) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        Set<String> lista = validacionCliente.validarListaGenericaCliente(cliente.getRecomendados());
        for (String s : lista) {
            Negocio negocio = validacionNegocio.buscarNegocio(s);
            negocioDTO = new DetalleNegocioDTO(
                    negocio.getNombre(),
                    negocio.getUbicacion(),
                    negocio.getDescripcion(),
                    negocio.getHorarios(),
                    negocio.getTelefonos(),
                    negocio.getImagenes());
        }
        return negocioDTO;
    }

    @Override
    public Set<ItemNegocioDTO> listarRecomendados(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        Set<String> recomendados = validacionCliente.validarListaGenericaCliente(cliente.getRecomendados());
        Set<ItemNegocioDTO> lista = new HashSet<>();
        for (String s : recomendados) {
            Negocio negocio = validacionNegocio.buscarNegocio(s);
            lista.add(new ItemNegocioDTO(
                    negocio.getCodigo(),
                    negocio.getNombre(),
                    negocio.getTipoNegocio()));
        }
        return lista;
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
    public Set<ItemNegocioDTO> listarFavoritos(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        Set<String> favoritos = validacionCliente.validarListaGenericaCliente(cliente.getFavoritos());
        Set<ItemNegocioDTO> lista = new HashSet<>();
        for (String s : favoritos) {
            Negocio negocio = validacionNegocio.buscarNegocio(s);
            lista.add(new ItemNegocioDTO(
                    negocio.getCodigo(),
                    negocio.getNombre(),
                    negocio.getTipoNegocio()
            ));
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

    /*private String formatearFecha(LocalDateTime fecha) throws Exception {

        if (fecha.isAfter(LocalDateTime.now())) {
            throw new ResourceInvalidException("Fecha no válida");
        }
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm.000 a", Locale.ENGLISH);
        String fechaFormateada = formatoFecha.format(fecha);
        return fechaFormateada;
    }
*/
}