package co.edu.uniquindio.proyecto.servicios.excepciones;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidacionCliente {

    private final ClienteRepo clienteRepo;
    private final ComentarioRepo comentarioRepo;
    private final HistorialRevisionRepo historialRevisionRepo;
    private final ModeradorRepo moderadorRepo;
    private final NegocioRepo negocioRepo;


    //Este método se usa para verificar el correo ya se encuentra registrado con otro
    // cliente al momento de registrar un nuevo cliente
    public void existeEmail(String email) throws Exception {

        Optional<Cliente> clienteOptional = clienteRepo.findByEmail(email);
        if (!clienteOptional.isPresent())
            throw new Exception("El usuario con email " + email + " ya se encuentra registrado");
    }

    public void validarUnicos(String email, String nickname) throws Exception {
        if (estaRepetidoEmail(email)) {
            throw new ResourceNotFoundException("El email " + email + " ya esta en uso");
        }
        if (estaRepetidoNickname(nickname)) {
            throw new ResourceNotFoundException("El nickname " + nickname + " ya esta en uso");
        }
    }

    public Cliente buscarCliente(String codigoCliente) throws Exception {

        Optional<Cliente> buscado = clienteRepo.findByCodigo(codigoCliente);
        Cliente cliente = null;

        if (buscado != null && buscado.get().getEstadoRegistro().equals(EstadoRegistro.ACTIVO)) {
            cliente = buscado.get();
        }
        if (buscado == null || buscado.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)) {
            throw new ResourceNotFoundException("No existe cliente con el codigo " + codigoCliente);
        }
        return cliente;
    }

    public Moderador buscarModerador(String codigoModerador) throws Exception {

        Optional<Moderador> buscado = moderadorRepo.findByCodigo(codigoModerador);
        Moderador moderador = new Moderador();

        if (buscado != null && buscado.get().getEstadoRegistro().equals(EstadoRegistro.ACTIVO)) {
            moderador = buscado.get();
        }
        if (buscado == null || buscado.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)) {
            throw new ResourceNotFoundException("No existe el moderador");
        }
        return moderador;
    }

    //Metodo para listar y mostrar los negocios de un cliente
    public List<String> listarNegociosCliente(String codigoCliente) throws Exception {
        Cliente cliente = buscarCliente(codigoCliente);
        List<String> lista = cliente.getNegocios();
        if (lista.isEmpty()) {
            throw new ResourceNotFoundException("No existe negocios asociados al cliente");
        }
        return lista;
    }

    //Metodo para listar los negocios favoritos o recomendados que tenga un cliente
    public Set<String> validarListaGenericaCliente(Set<String> lista) throws Exception {

        if (lista.isEmpty()) {
            throw new ResourceNotFoundException("El cliente no tiene favoritos o recomendados");
        }
        return lista;
    }

    //Metodo para obtener el listado de negocios en el estado actual
    public List<String> obtenerListadoNegociosCliente(String codigoCliente) throws Exception {

        try {
            Cliente cliente = buscarCliente(codigoCliente);
            List<String> lista = cliente.getNegocios();
            return lista;
        } catch (Exception ex) {
            throw new Exception("No se puede hallar el listado de negocios", ex);
        }
    }

    private boolean estaRepetidoEmail(String email) throws Exception {
        return clienteRepo.findByEmail(email).isPresent();
    }

    private boolean estaRepetidoNickname(String nickname) throws Exception {
        return clienteRepo.findByNickname(nickname).isPresent();
    }
} 
