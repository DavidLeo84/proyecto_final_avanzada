package co.edu.uniquindio.proyecto.servicios.excepciones;

import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.repositorios.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Validacion {

    private final ClienteRepo clienteRepo;
    private final ComentarioRepo comentarioRepo;
    private final HistorialRevisionRepo historialRevisionRepo;
    private final ModeradorRepo moderadorRepo;
    private final NegocioRepo negocioRepo;


    public void existeCliente(String nickname) throws Exception {

        Optional<Cliente> clienteOptional = clienteRepo.findByNickname(nickname);
        if (clienteOptional == null)
            throw new Exception
                    ("El usuario con nickname " + nickname + " ya se encuentra registrado");
    }

    public void existeEmail(String email) throws Exception {

        Optional<Cliente> cliente = clienteRepo.findByEmail(email);
        if (cliente != null) {
            throw new Exception("El usuario con " + email + " ya existe");
        }
    }

    public Cliente buscarCliente(String nickname)throws Exception {

       Optional <Cliente> buscado = clienteRepo.findByNickname(nickname);

        if (buscado != null) {
            throw new Exception("No existe cliente con el nickname " + nickname);
        }
        Cliente cliente = buscado.get();
        return cliente;
    }
}
