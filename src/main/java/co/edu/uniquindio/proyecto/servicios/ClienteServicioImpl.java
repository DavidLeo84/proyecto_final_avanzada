package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.*;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.Rol;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.interfaces.IClienteServicio;

import co.edu.uniquindio.proyecto.servicios.interfaces.ICloudinaryServicio;
import co.edu.uniquindio.proyecto.servicios.interfaces.IEmailServicio;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteServicioImpl implements IClienteServicio {

    private final ClienteRepo clienteRepo;
    private final ModeradorRepo moderadorRepo;
    private final ValidacionCliente validacion;
    private final IEmailServicio emailServicio;
    private final ICloudinaryServicio cloudinaryServicio;

    @Override
    public void iniciarSesion(SesionDTO sesionDTO) throws Exception {

    }

    //Metodo para eliminar la cuenta del moderador
    @Override
    public void eliminarCuenta(String codigo) throws Exception {
        try {
            Moderador moderador = validacion.buscarModerador(codigo);
            moderador.setEstadoRegistro(EstadoRegistro.INACTIVO);
            moderadorRepo.save(moderador);
        } catch (ResourceNotFoundException e) {
            e.getMessage();
        }
    }

    //Metodo pendiente por implementar en test
    @Override
    public void enviarLinkRecuperacion(String destinatario) throws Exception {

        validacion.existeEmail(destinatario);
        Optional<Cliente> clienteOptional = clienteRepo.findByEmail(destinatario);
        //Cliente cliente = clienteOptional.get();
        emailServicio.enviarEmail(destinatario, "Recuperar contraseña",
                "http://localhost:8080/auth/recoPass");
    }

    @Override
    public void cambiarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception {

    }

    @Override
    public Cliente registrarse(RegistroClienteDTO clienteDTO) throws Exception {

        validacion.existeCliente(clienteDTO.nickname());
        validacion.existeEmail(clienteDTO.email());
        ///BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //String passwordEncriptada = passwordEncoder.encode(clienteDTO.password());
        Cliente cliente = Cliente.builder()
                .email(clienteDTO.email()).password(clienteDTO.password())
                .estadoRegistro(EstadoRegistro.ACTIVO).rol(Rol.USUARIO)
                .nickname(clienteDTO.nickname()).nombre(clienteDTO.nombre())
                .ciudad(clienteDTO.ciudad()).fotoPerfil(clienteDTO.fotoPerfil())
                .negocios(new HashSet<String>()).favoritos(new HashSet<>())
                .recomendados(new HashSet<>()).build();
        clienteRepo.save(cliente);
        return cliente;
    }

    @Override
    public Cliente editarPerfil(DetalleClienteDTO clienteDTO, String codigo) throws Exception {

        Cliente cliente = validacion.buscarCliente(codigo);
        cliente.setNombre(clienteDTO.nombre());
        cliente.setCiudad(clienteDTO.ciudad());
        cliente.setFotoPerfil(cliente.getFotoPerfil());
        clienteRepo.save(cliente);
        return cliente;
    }

    //Metodo para eliminar la cuenta del cliente
    @Override
    public void eliminarPerfil(String codigo) throws Exception {
        try {
            Cliente cliente = validacion.buscarCliente(codigo);
            cliente.setEstadoRegistro(EstadoRegistro.INACTIVO);
            clienteRepo.save(cliente);
        } catch (ResourceNotFoundException e) {
            e.getMessage();
        }
    }

    @Override
    public DetalleClienteDTO obtenerUsuario(String codigo) throws Exception {

        Cliente cliente = validacion.buscarCliente(codigo);

        return new DetalleClienteDTO(
                cliente.getNombre(),
                cliente.getCiudad(),
                cliente.getFotoPerfil()
        );
    }
}
