package co.edu.uniquindio.proyecto.servicios.excepciones;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import co.edu.uniquindio.proyecto.repositorios.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

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

    @Autowired
    private ValidacionNegocio validacionNegocio;

    public void existeEmail(String email) throws Exception {

        Optional<Cliente> clienteOptional = clienteRepo.findByEmail(email);
        if (!clienteOptional.get().getEmail().equals(email)) {
            throw new Exception("El correo no se encuentra registrado");
        }
    }

    public void validarEmail(String email, String codigoCliente) throws Exception {

        Optional<Cliente> clienteOptional = clienteRepo.findByEmail(email);
        if (!clienteOptional.get().getCodigo().equals(codigoCliente)) {
            throw new NoSuchElementException("El correo ya se encuentra registrado");
        }
    }

    public Cliente buscarPorEmail(String email) throws Exception {

        Optional<Cliente> clienteOptional = clienteRepo.findByEmail(email);
        if (clienteOptional.isEmpty()) {
            throw new Exception("El correo no se encuentra registrado");
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


    //Metodo para validar si la lista no tiene negocios aprobados y es apta para eliminar la cuenta del cliente
    public boolean validarListaNegociosCliente(String codigoCliente) throws Exception {
        boolean respuesta = true;
        Cliente cliente = buscarCliente(codigoCliente);
        List<String> lista = cliente.getNegocios();
        for (String codigo : lista) {
            Optional<Negocio> optional = negocioRepo.findByCodigoNegocio(codigo);
            if (optional.get().getEstadoNegocio().equals(EstadoNegocio.APROBADO.name())) {
                throw new ResourceInvalidException("Error! Hay negocios asociados que impiden eliminar la cuenta");
            }
        }
        return respuesta;
    }

    //Metodo para listar los negocios favoritos o recomendados que tenga un cliente
    public Set<String> validarListaGenericaCliente(Set<String> lista) throws Exception {

        if (lista.isEmpty()) {
            throw new ResourceNotFoundException("El cliente no tiene favoritos o recomendados");
        }
        return lista;
    }

    public String validarNegocioRecomendado(Negocio negocio, Cliente cliente) throws Exception {

        Set<String> listaNegocios = validarListaGenericaCliente(cliente.getRecomendados());
        String recomendado = "";
        for (String codigo : listaNegocios) {
            if (codigo.equals(negocio.getCodigoNegocio())) {
                recomendado = codigo;
            }
        }
        if (recomendado == "") {
            throw new ResourceNotFoundException("No existe este negocio en su lista de recomendados");
        }
        return recomendado;
    }

    public String validarNegocioFavorito(String codigoNegocio, String codigoCliente) throws Exception {

        Cliente cliente = buscarCliente(codigoCliente);
        Negocio negocio = validacionNegocio.buscarNegocio(codigoNegocio);
        Set<String> listaNegocios = validarListaGenericaCliente(cliente.getFavoritos());
        String favorito = "";
        for (String codigo : listaNegocios) {
            if (codigo.equals(codigoNegocio)) {
                favorito = codigo;
            }
        }
        if (favorito == "") {
            throw new ResourceNotFoundException("No existe este negocio en su lista de favoritos");
        }
        return favorito;
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
