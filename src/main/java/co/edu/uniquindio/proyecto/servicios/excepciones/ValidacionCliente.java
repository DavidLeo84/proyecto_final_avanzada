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


    public void existeEmail(String email) throws Exception {

        Optional<Cliente> clienteOptional = clienteRepo.findByEmail(email);
        if (clienteOptional == null)
            throw new Exception("El correo no se encuentra registado");
    }

    public void validarEmail(String email) throws Exception {

        Optional<Cliente> clienteOptional = clienteRepo.findByEmail(email);
        if (clienteOptional.isPresent())
            throw new Exception("El correo ya se encuentra registado");
    }

    public Cliente buscarPorEmail(String email) throws Exception {

        Optional<Cliente> clienteOptional = clienteRepo.findByEmail(email);
        if (clienteOptional == null) {
            throw new ResourceNotFoundException("El correo no se encuentra registrado");
        }
        Cliente cliente = clienteOptional.get();
        return cliente;
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
        if (buscado.isEmpty() || buscado.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)) {
            throw new ResourceNotFoundException("No existe cliente");
        }
        Cliente cliente = buscado.get();
        return cliente;
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
