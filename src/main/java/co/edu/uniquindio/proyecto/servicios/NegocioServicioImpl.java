package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.ActualizarNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.DetalleNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroHistorialDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroNegocioDTO;
import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.modelo.Calificacion;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Comentario;
import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionNegocio;
import co.edu.uniquindio.proyecto.servicios.interfaces.INegocioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class NegocioServicioImpl implements INegocioServicio {

    private final ValidacionNegocio validacionNegocio;
    private final ValidacionCliente validacionCliente;
    private final NegocioRepo negocioRepo;
    private final ClienteRepo clienteRepo;
    private Set<DetalleNegocioDTO> listaNegocios = new HashSet<>();
    private DetalleNegocioDTO negocioDTO = null;

    @Override
    public Negocio crearNegocio(RegistroNegocioDTO negocioDTO) throws Exception {

        validacionNegocio.existeNegocio(negocioDTO.ubicacion().getLongitud(), negocioDTO.ubicacion().getLatitud());
        Cliente cliente = validacionCliente.buscarCliente(negocioDTO.codigoCliente());
        Negocio nuevo = Negocio.builder().estado(EstadoNegocio.APROBADO).ubicacion(negocioDTO.ubicacion())
                .codigoCliente(cliente.getCodigo()).nombre(negocioDTO.nombre())
                .descripcion(negocioDTO.descripcion()).tipoNegocio(TipoNegocio.valueOf(negocioDTO.tipoNegocio()))
                .horarios(negocioDTO.horarios()).telefonos(negocioDTO.telefonos())
                .imagenes(negocioDTO.imagenes()).comentarios(new HashSet<Comentario>())
                .calificaciones(new HashSet<Calificacion>()).historialRevisiones(new HashSet<HistorialRevision>())
                .build();
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
        negocio.setEstado(EstadoNegocio.ELIMINADO);
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

    @Override
    public Set<DetalleNegocioDTO> filtrarPorEstado(EstadoNegocio estado) throws Exception {

        validacionNegocio.validarListaNegociosEstado(estado);
        Set<Negocio> negocios = negocioRepo.findAllByEstado(estado);
        Set<DetalleNegocioDTO> negocioDTOList = new HashSet<>();
        return negocios.stream().map(negocio -> new DetalleNegocioDTO(
                negocio.getNombre(), negocio.getUbicacion(),
                negocio.getDescripcion(), negocio.getHorarios(),
                negocio.getTelefonos(), negocio.getImagenes()
        )).collect(Collectors.toSet());
    }

    @Override
    public Set<DetalleNegocioDTO> listarNegociosPropietario(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        validacionCliente.validarListaNegociosCliente(codigoCliente);
        Set<Negocio> negocios = negocioRepo.findAllByCodigoCliente(codigoCliente);
        return negocios.stream().map(negocio -> new DetalleNegocioDTO(
                negocio.getNombre(), negocio.getUbicacion(),
                negocio.getDescripcion(), negocio.getHorarios(),
                negocio.getTelefonos(), negocio.getImagenes()
        )).collect(Collectors.toSet());
    }

    @Override
    public void cambiarEstado(String codigoNegocio, EstadoNegocio estado) throws Exception {

        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        negocio.setEstado(estado);
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
    public Set<DetalleNegocioDTO> listarRecomendados(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        Set<String> recomendados = validacionCliente.validarListaGenericaCliente(cliente.getRecomendados());
        for (String s : recomendados) {
            Negocio negocio = validacionNegocio.buscarNegocio(s);
            listaNegocios.add(new DetalleNegocioDTO(
                    negocio.getNombre(),
                    negocio.getUbicacion(),
                    negocio.getDescripcion(),
                    negocio.getHorarios(),
                    negocio.getTelefonos(),
                    negocio.getImagenes()));
        }
        return listaNegocios;
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
    public Set<DetalleNegocioDTO> listarFavoritos(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        Set<String> favoritos = validacionCliente.validarListaGenericaCliente(cliente.getFavoritos());
        for (String s : favoritos) {
            Negocio negocio = validacionNegocio.buscarNegocio(s);
            listaNegocios.add(new DetalleNegocioDTO(
                    negocio.getNombre(),
                    negocio.getUbicacion(),
                    negocio.getDescripcion(),
                    negocio.getHorarios(),
                    negocio.getTelefonos(),
                    negocio.getImagenes()));
        }
        return listaNegocios;
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

}