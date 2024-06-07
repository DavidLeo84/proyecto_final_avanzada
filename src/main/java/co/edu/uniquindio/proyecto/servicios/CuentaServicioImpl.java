package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.LoginDTO;
import co.edu.uniquindio.proyecto.dtos.TokenDTO;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.interfaces.ICuentaServicio;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CuentaServicioImpl implements ICuentaServicio {

    private ClienteRepo clienteRepo;
    private ModeradorRepo moderadorRepo;
    private ValidacionCliente validacionCliente;
    private EmailServicioImpl emailServicio;
    private CloudinaryServicioImpl cloudinaryServicio;
    private AutenticacionServicioImpl autenticacionServicio;

    public CuentaServicioImpl(ClienteRepo clienteRepo, ModeradorRepo moderadorRepo,
                              ValidacionCliente validacionCliente, EmailServicioImpl emailServicio,
                              CloudinaryServicioImpl cloudinaryServicio, AutenticacionServicioImpl autenticacionServicio) {
        this.clienteRepo = clienteRepo;
        this.moderadorRepo = moderadorRepo;
        this.validacionCliente = validacionCliente;
        this.emailServicio = emailServicio;
        this.cloudinaryServicio = cloudinaryServicio;
        this.autenticacionServicio = autenticacionServicio;
    }

    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception {

        TokenDTO token = null;
        Optional<Cliente> clienteOptional = clienteRepo.findByEmail(loginDTO.email());
        Cliente cliente = null;
        if (clienteOptional.isPresent()) {
            cliente = clienteOptional.get();
            token = autenticacionServicio.iniciarSesionCliente(loginDTO);
            return token;
        }
        Optional<Moderador> moderadorOptional = moderadorRepo.findByEmail(loginDTO.email());
        Moderador moderador = null;

        if (moderadorOptional.isPresent()) {
            moderador = moderadorOptional.get();
            token = autenticacionServicio.iniciarSesionModerador(loginDTO);
            return token;
        }
        if (!moderadorOptional.isPresent() && !clienteOptional.isPresent()) {
            throw new ResourceNotFoundException("El usuario no se encuentra registrado");
        }
        return token;
    }

    @Override
    public TokenDTO enviarLinkRecuperacion(String email) throws Exception {

        TokenDTO token = null;
        Optional<Cliente> clienteOptional = clienteRepo.findByEmail(email);
        Cliente cliente = null;
        if (clienteOptional.isPresent()) {
            cliente = clienteOptional.get();
            token = autenticacionServicio.recuperarPasswordCliente(cliente.getEmail());
            emailServicio.enviarEmail(email, "Recuperar contraseña",
                    "http://localhost:8080/api/auth/recoPass");
            return token;
        }
        Optional<Moderador> moderadorOptional = moderadorRepo.findByEmail(email);
        Moderador moderador = null;

        if (moderadorOptional.isPresent()) {
            moderador = moderadorOptional.get();
            token = autenticacionServicio.recuperarPasswordModerador(moderador.getEmail());
            emailServicio.enviarEmail(email, "Recuperar contraseña",
                    "http://localhost:8080/api/auth/recoPass");
            return token;
        }
        if (!moderadorOptional.isPresent() && !clienteOptional.isPresent()) {
            throw new ResourceNotFoundException("El usuario no se encuentra registrado");
        }

        return token;
    }
}
