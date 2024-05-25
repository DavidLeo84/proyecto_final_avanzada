package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.*;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
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
    private DetalleNegocioDTO negocioDTO = null;

    @Override
    public Negocio crearNegocio(RegistroNegocioDTO negocioDTO) throws Exception {

        validacionNegocio.existeCoordenadas(negocioDTO.ubicacion().getLatitud(), negocioDTO.ubicacion().getLongitud());
        Cliente cliente = validacionCliente.buscarCliente(negocioDTO.codigoCliente());
        Negocio nuevo = Negocio.builder().estadoNegocio(EstadoNegocio.PENDIENTE).ubicacion(negocioDTO.ubicacion())
                .codigoCliente(cliente.getCodigo()).nombre(negocioDTO.nombre().toLowerCase())
                .descripcion(negocioDTO.descripcion().toLowerCase()).tipoNegocios(negocioDTO.tipoNegocios())
                .horarios(negocioDTO.horarios()).telefonos(negocioDTO.telefonos())
                .imagenes(negocioDTO.imagenes()).calificaciones(new ArrayList<String>())
                .historialRevisiones(new ArrayList<HistorialRevision>()).recomendaciones(new ArrayList<>())
                .calificacion(0).build();
        nuevo.getHistorialRevisiones().add(new HistorialRevision(
                "", EstadoNegocio.PENDIENTE, validacionModerador.formatearFecha(LocalDateTime.now()),
                "default", ""));
        negocioRepo.save(nuevo);
        cliente.getNegocios().add(nuevo.getCodigo());
        clienteRepo.save(cliente);
        return nuevo;
    }

    @Override
    public void actualizarNegocio(ActualizarNegocioDTO negocioDTO) throws Exception {

        Optional<Negocio> negocioOptional = negocioRepo.findByCodigo(negocioDTO.codigo());
        if (negocioOptional.isEmpty()) {
            throw new ResourceNotFoundException("No existe el negocio");
        }
        Negocio negocio = null;
        if (negocioOptional.get().getEstadoNegocio().equals(EstadoNegocio.RECHAZADO)) {
            negocio = negocioOptional.get();
        }
        if (negocioOptional.get().getEstadoNegocio().equals(EstadoNegocio.APROBADO)) {
            negocio = negocioOptional.get();
        }
        negocio.setDescripcion(negocioDTO.descripcion());
        negocio.setUbicacion(negocioDTO.ubicacion());
        negocio.setHorarios(negocioDTO.horarios());
        negocio.setTelefonos(negocioDTO.telefonos());
        negocio.setImagenes(negocioDTO.imagenes());
        negocio.setEstadoNegocio(EstadoNegocio.PENDIENTE);
        negocioRepo.save(negocio);
    }

    @Override
    public void eliminarNegocio(String codigoNegocio) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        Cliente cliente = validacionCliente.buscarCliente(negocio.getCodigoCliente());
        List<String> listaNegociosCliente = cliente.getNegocios();
        for (String codigo : listaNegociosCliente) {
            if (codigoNegocio.equals(codigo)) {
                negocio.setEstadoNegocio(EstadoNegocio.ELIMINADO);
                negocioRepo.save(negocio);
            }
        }
    }

    @Override
    public DetalleNegocioDTO obtenerNegocio(String codigoNegocio) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        return new DetalleNegocioDTO(negocio.getCodigo(),
                negocio.getNombre(), negocio.getTipoNegocios(),
                negocio.getUbicacion(), negocio.getDescripcion(),
                negocio.getCalificacion(), negocio.getHorarios(),
                negocio.getTelefonos(), negocio.getImagenes()
        );
    }

    @Override
    public List<ItemNegocioDTO> filtrarPorEstado(EstadoNegocio estado) throws Exception {

        validacionNegocio.validarEstadoListaNegocios(estado);
        List<Negocio> negocios = negocioRepo.findAllByEstadoNegocio(estado);
        List<DetalleNegocioDTO> negocioDTOList = new ArrayList<>();
        return negocios.stream()
                .map(n -> new ItemNegocioDTO(
                        n.getCodigo(),
                        n.getNombre(),
                        n.getTipoNegocios(),
                        n.getUbicacion()))
                .collect(Collectors.toList());
    }


    @Override
    public List<ItemNegocioDTO> listarNegociosPropietario(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        List<Negocio> listaNegociosCliente = negocioRepo.findAllByCodigoCliente(codigoCliente);
        List<Negocio> listaNegocios = new ArrayList<>();
        for (Negocio n : listaNegociosCliente) {
            if (n.getEstadoNegocio().equals(EstadoNegocio.APROBADO)) {
                listaNegocios.add(n);
            }
        }
        return listaNegocios.stream()
                .map(n -> new ItemNegocioDTO(
                        n.getCodigo(),
                        n.getNombre(),
                        n.getTipoNegocios(),
                        n.getUbicacion())
                ).collect(Collectors.toList());
    }

    @Override
    public void guardarRecomendado(String codigoNegocio, String codigoCliente) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        negocio.getRecomendaciones().add(1);
        cliente.getRecomendados().add(codigoNegocio);
        negocioRepo.save(negocio);
        clienteRepo.save(cliente);
    }

    //Metodo para visualizar un negocio recomendado por parte del cliente que lo recomendó
    @Override
    public DetalleNegocioDTO obtenerRecomendado(String codigoNegocio, String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        Set<String> lista = validacionCliente.validarListaGenericaCliente(cliente.getRecomendados());
        for (String codigo : lista) {
            if (codigoNegocio.equals(codigo)) {
                Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
                negocioDTO = new DetalleNegocioDTO(negocio.getCodigo(),
                        negocio.getNombre(), negocio.getTipoNegocios(),
                        negocio.getUbicacion(), negocio.getDescripcion(),
                        negocio.getCalificacion(), negocio.getHorarios(),
                        negocio.getTelefonos(), negocio.getImagenes());
            }

        }
        return negocioDTO;
    }

    @Override
    public String eliminarNegocioRecomendado(String codigoNegocio, String codigoCliente) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        String codigoRecomendado = validacionCliente.validarNegocioRecomendado(codigoNegocio, codigoCliente);
        cliente.getRecomendados().remove(codigoRecomendado);
        clienteRepo.save(cliente);
        negocio.getRecomendaciones().remove(0);
        negocioRepo.save(negocio);
        return "El negocio fue eliminado de su lista de recomendados con éxito";
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
                    negocio.getTipoNegocios(),
                    negocio.getUbicacion()));
        }
        return lista;
    }

    //Metodo para listar los negocios mas recomendados por los clientes -funcionalidad adicional del proyecto
    public List<ItemNegocioDTO> listaNegociosRecomendadosPorClientes() throws Exception {

        List<Negocio> negocios = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.APROBADO);
        return negocios
                .stream()
                .filter(n -> n.getRecomendaciones().size() > 4)
                .map(n -> new ItemNegocioDTO(
                        n.getCodigo(),
                        n.getNombre(),
                        n.getTipoNegocios(),
                        n.getUbicacion()))
                .collect(Collectors.toList());
    }

    //comun para cliente y moderador
    @Override
    public DetalleRevisionDTO obtenerRevision(ItemRevisionDTO item) throws Exception {

        HistorialRevision revision = validacionNegocio.buscarRevision(item.codigoNegocio(), item.fecha());
        return new DetalleRevisionDTO(
                revision.getDescripcion(),
                revision.getEstadoNegocio(),
                revision.getFecha());
    }

    //comun para cliente y moderador
    @Override
    public List<ItemRevisionDTO> listarRevisiones(String codigoNegocio) throws Exception {

        List<HistorialRevision> lista = validacionNegocio.validarListaHistorialRevision(codigoNegocio);
        return lista
                .stream()
                .map(hr -> new ItemRevisionDTO(
                        hr.getCodigoNegocio(),
                        hr.getFecha()))
                .collect(Collectors.toList());
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

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        String codigoFavorito = validacionCliente.validarNegocioFavorito(codigoNegocio, codigoCliente);
        cliente.getFavoritos().remove(codigoFavorito);
        clienteRepo.save(cliente);
        return "El negocio fue eliminado de su lista de favoritos con éxito";
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
                    negocio.getTipoNegocios(),
                    negocio.getUbicacion()));
        }
        return lista;
    }

    @Override
    public DetalleNegocioDTO obtenerFavorito(String codigoNegocio, String codigoCliente) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        Set<String> lista = validacionCliente.validarListaGenericaCliente(cliente.getRecomendados());
        for (String codigo : lista) {
            if (codigoNegocio.equals(codigo)) {
                negocioDTO = new DetalleNegocioDTO(negocio.getCodigo(),
                        negocio.getNombre(), negocio.getTipoNegocios(),
                        negocio.getUbicacion(), negocio.getDescripcion(),
                        negocio.getCalificacion(), negocio.getHorarios(),
                        negocio.getTelefonos(), negocio.getImagenes());
            }

        }
        return negocioDTO;
    }

    @Override
    public String determinarDisponibilidadNegocio(String codigoNegocio) throws Exception {

        String estado = "";
        LocalDateTime fechaSistema = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String fechaHoy = fechaSistema.toLocalTime().format(formatter);
        LocalTime horaHoy = transformarHora(fechaHoy);
        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        List<Horario> lista = negocio.getHorarios();
        for (Horario h : lista) {
            if (h.getDia().equals(fechaSistema.getDayOfWeek().name())) {
                if (horaHoy.isAfter(transformarHora(h.getHoraInicio())) && horaHoy.isBefore(transformarHora(h.getHoraFin()))) {
                    estado = "Abierto";
                } else {
                    estado = "Cerrado";
                }
            }
        }
        if (estado == "") {
            throw new ResourceNotFoundException("Error, el negocio no tiene horario vigente");
        }
        return estado;
    }

    //comun para cliente y moderador
    @Override
    public DetalleNegocioDTO buscarNegocioPorNombre(String nombreNegocio) throws Exception {

        Negocio negocio = validacionNegocio.validarNegocioPorNombre(nombreNegocio.toLowerCase());
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
    public List<ItemNegocioDTO> listarNegociosAbiertosPorTipoSegunHora(TipoNegocio tipoNegocio) throws Exception {

        List<Negocio> negocios = validacionNegocio.validarListaNegociosPorTipo(tipoNegocio);

        for (Negocio n : negocios) {
            String estado = determinarDisponibilidadNegocio(n.getCodigo());
        }
        return negocios.stream().map(n -> new ItemNegocioDTO(
                        n.getCodigo(),
                        n.getNombre(),
                        n.getTipoNegocios(),
                        n.getUbicacion()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listarTiposNegocio() throws Exception {

        List<String> lista = new ArrayList<>();
        for (TipoNegocio tipo : TipoNegocio.values()) {
            System.out.println(tipo);
            lista.add(tipo.name());
        }
        return lista;
    }

    @Override
    public void calificarNegocio(String codigoNegocio, ValorCalificar calificar) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        validacionNegocio.validarCalificacionNegocio(calificar);
        negocio.getCalificaciones().add(calificar.name());
        negocioRepo.save(negocio);
        calcularPromedioCalificaficaciones(codigoNegocio);
    }

    @Override
    public int calcularPromedioCalificaficaciones(String codigoNegocio) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);

//        List<Negocio> listaNegocios = validacionNegocio.validarListaGenericaNegocios(EstadoNegocio.APROBADO).stream()
//                .filter(n -> n.getCalificaciones().size() > 0).collect(Collectors.toList());
//        int suma = 0;
//        for (Negocio n : listaNegocios) {
        int suma = negocio.getCalificaciones().stream()
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
                        case "DEFAULT":
                            return 0;
                    }
                    return 0;
                })
                .sum();
        int total = suma / negocio.getCalificaciones().size();
        System.out.println("n.getCalificaciones().size() = " + negocio.getCalificaciones().size());
        negocio.setCalificacion(total);
        negocioRepo.save(negocio);
        return total;
    }

    @Override
    public List<ItemNegocioDTO> listarNegociosPorPalabraComun(String palabra) throws Exception {

        List<Negocio> listaNegocios = negocioRepo.findByNombreContainingIgnoreCase(palabra);
        if (listaNegocios.isEmpty()) {
            throw new ResourceNotFoundException("No hay negocios que coincidan con la palabra " + palabra);
        }
        return listaNegocios
                .stream()
                .map(n -> new ItemNegocioDTO(
                        n.getCodigo(),
                        n.getNombre(),
                        n.getTipoNegocios(),
                        n.getUbicacion()))
                .collect(Collectors.toList());
    }

    private LocalTime transformarHora(String hora) throws Exception {

        try {

            DateTimeFormatter formatohora = DateTimeFormatter.ISO_TIME.withLocale(Locale.ENGLISH);
            LocalTime horaLocalTime = LocalTime.parse(hora, formatohora);
            return LocalTime.from(horaLocalTime);
            //return horaLocalTime;
        } catch (Exception ex) {
            throw new Exception("La hora no cumple con el formato requerido");
        }
    }
}

