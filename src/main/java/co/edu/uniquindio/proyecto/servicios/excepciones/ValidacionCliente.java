package co.edu.uniquindio.proyecto.servicios.excepciones;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.repositorios.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidacionCliente {

    private final ClienteRepo clienteRepo;
    private final ComentarioRepo comentarioRepo;
    private final HistorialRevisionRepo historialRevisionRepo;
    private final ModeradorRepo moderadorRepo;
    private final NegocioRepo negocioRepo;


    //Este metodo se usa al registrar un nuevo cliente si existe ya un registro con su
    // mismo nickname
    public void existeCliente(String nickname) throws Exception {

        Optional<Cliente> clienteOptional = clienteRepo.findByNickname(nickname);
        if (!clienteOptional.isEmpty())
            throw new Exception
                    ("El usuario con nickname " + nickname + " ya se encuentra registrado");
    }

    //Este método se usa para verificar el correo ya se encuentra registrado con otro
    // cliente al momento de registrar un nuevo cliente
    public void existeEmail(String email) throws Exception {

        Optional<Cliente> clienteOptional = clienteRepo.findByEmail(email);
        if (!clienteOptional.isEmpty())
            throw new Exception
                    ("El usuario con email " + email + " ya se encuentra registrado");
    }

    public Cliente buscarCliente(int codigo) throws Exception {

        Optional<Cliente> buscado = clienteRepo.findByCodigo(codigo);
        Cliente cliente = null;

        if (buscado != null && buscado.get().getEstadoRegistro().equals(EstadoRegistro.ACTIVO)) {
            cliente = buscado.get();
        }
        if (buscado == null || buscado.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)) {
            throw new ResourceNotFoundException("No existe cliente con el codigo " + codigo);
        }
        return cliente;
    }

    public Moderador buscarModerador(int codigo) throws Exception {

        Optional<Moderador> buscado = moderadorRepo.findByCodigo(codigo);
        Moderador moderador = new Moderador();

        if (buscado != null && buscado.get().getEstadoRegistro().equals(EstadoRegistro.ACTIVO)) {
            moderador = buscado.get();
        }
        if (buscado == null || buscado.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)) {
            throw new ResourceNotFoundException("No existe el moderador");
        }
        return moderador;
    }

} 
