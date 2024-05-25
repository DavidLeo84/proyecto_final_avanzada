package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.*;

import co.edu.uniquindio.proyecto.enums.CiudadEnum;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.PermisoEnum;
import co.edu.uniquindio.proyecto.enums.RolEnum;
import co.edu.uniquindio.proyecto.modelo.Rol;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.interfaces.IAutenticacionServicio;
import co.edu.uniquindio.proyecto.servicios.interfaces.IClienteServicio;

import co.edu.uniquindio.proyecto.servicios.interfaces.ICloudinaryServicio;
import co.edu.uniquindio.proyecto.servicios.interfaces.IEmailServicio;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ClienteServicioImpl implements IClienteServicio {

    private ClienteRepo clienteRepo;
    private ModeradorRepo moderadorRepo;
    private ValidacionCliente validacionCliente;


    private EmailServicioImpl emailServicio;
    private CloudinaryServicioImpl cloudinaryServicio;
    private AutenticacionServicioImpl autenticacionServicio;

    public ClienteServicioImpl(ClienteRepo clienteRepo, ModeradorRepo moderadorRepo, ValidacionCliente validacionCliente,
                               EmailServicioImpl emailServicio, CloudinaryServicioImpl cloudinaryServicio,
                               AutenticacionServicioImpl autenticacionServicio) {
        this.clienteRepo = clienteRepo;
        this.moderadorRepo = moderadorRepo;
        this.validacionCliente = validacionCliente;
        this.emailServicio = emailServicio;
        this.cloudinaryServicio = cloudinaryServicio;
        this.autenticacionServicio = autenticacionServicio;
    }

    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception {

        TokenDTO token = autenticacionServicio.iniciarSesionCliente(loginDTO);

        return token;
    }

    @Override
    public void eliminarCuenta(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        List<String> lista = validacionCliente.obtenerListadoNegociosCliente(codigoCliente);
        if (lista.isEmpty()) {
            cliente.setEstadoRegistro(EstadoRegistro.INACTIVO);
            clienteRepo.save(cliente);
        } else {
            throw new ResourceNotFoundException("Error! Hay negocios asociados que impiden eliminar la cuenta");
        }
    }

    //Metodo pendiente por implementar en test
    @Override
    public TokenDTO enviarLinkRecuperacion(String email) throws Exception {

        Optional<Cliente> clienteOptional = clienteRepo.findByEmail(email);
        Cliente cliente = null;
        if (clienteOptional.isPresent()) {
            cliente = clienteOptional.get();
        }
        Optional<Moderador> moderadorOptional = moderadorRepo.findByEmail(email);
        Moderador moderador = null;

        if (moderadorOptional.isPresent()) {
            moderador = moderadorOptional.get();
        }
        if (!moderadorOptional.isPresent() && !clienteOptional.isPresent()) {
            throw new ResourceNotFoundException("El usuario no se encuentra registrado");
        }
        emailServicio.enviarEmail(email, "Recuperar contraseña",
                "http://localhost:8080/api/recoPass");
        TokenDTO token = autenticacionServicio.recuperarPasswordCliente(email);
        return token;
    }

    @Override
    public String cambiarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(cambioPasswordDTO.codigoUsuario());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String nuevaPassword = passwordEncoder.encode(cambioPasswordDTO.passwordNueva());
        cliente.setPassword(nuevaPassword);
        clienteRepo.save(cliente);
        return "El password fue cambiado con éxito";
    }

    @Override
    public Cliente registrarse(RegistroClienteDTO clienteDTO) throws Exception {

        validacionCliente.validarUnicos(clienteDTO.email(), clienteDTO.nickname());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(clienteDTO.password());
        Cliente nuevo = Cliente.builder()
                .email(clienteDTO.email()).password(passwordEncriptada)
                .estadoRegistro(EstadoRegistro.ACTIVO).rol(Rol.builder().nombreRol(RolEnum.CLIENTE)
                        .permisos(Set.of(PermisoEnum.APROBAR, PermisoEnum.COMENTAR,
                                PermisoEnum.CALIFICAR, PermisoEnum.BUSCAR)).build())
                .nickname(clienteDTO.nickname()).nombre(clienteDTO.nombre().toLowerCase())
                .ciudad(clienteDTO.ciudad().name()).fotoPerfil(clienteDTO.fotoPerfil())
                .negocios(new ArrayList<>()).favoritos(new HashSet<>())
                .recomendados(new HashSet<>()).aprobacionesComentarios(new HashSet<>()).build();
        clienteRepo.save(nuevo);
        return nuevo;
    }

    @Override
    public Cliente actualizarCliente(ActualizarClienteDTO clienteDTO) throws Exception {

        validacionCliente.validarEmail(clienteDTO.email());
        Cliente cliente = validacionCliente.buscarCliente(clienteDTO.codigo());
        cliente.setNombre(clienteDTO.nombre());
        cliente.setCiudad(clienteDTO.ciudad().name());
        cliente.setEmail(clienteDTO.email());
        cliente.setFotoPerfil(clienteDTO.fotoPerfil());
        clienteRepo.save(cliente);
        return cliente;
    }

    @Override
    public DetalleClienteDTO obtenerUsuario(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        return new DetalleClienteDTO(
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getCiudad(),
                cliente.getFotoPerfil()
        );
    }

    @Override
    public List<String> listarCiudades() throws Exception {

        List<String> listaCiudades = new ArrayList<>();
        for (CiudadEnum ciudad : CiudadEnum.values()) {

            listaCiudades.add(ciudad.name());
        }
        return listaCiudades;
    }
}
