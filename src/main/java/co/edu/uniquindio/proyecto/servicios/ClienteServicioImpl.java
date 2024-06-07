package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.*;

import co.edu.uniquindio.proyecto.enums.CiudadEnum;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.PermisoEnum;
import co.edu.uniquindio.proyecto.enums.RolEnum;
import co.edu.uniquindio.proyecto.modelo.Rol;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.interfaces.IClienteServicio;
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
    public void eliminarCuenta(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        if (validacionCliente.validarListaNegociosCliente(codigoCliente) && cliente.getEstadoRegistro().equals(EstadoRegistro.ACTIVO)) {
            cliente.setEstadoRegistro(EstadoRegistro.INACTIVO);
            clienteRepo.save(cliente);
        }
    }

    @Override
    public String cambiarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(cambioPasswordDTO.codigoUsuario());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(cambioPasswordDTO.passwordActual(), cliente.getPassword())) {
            throw new NoSuchElementException("La contraseña es incorrecta");
        }
        String nuevaPassword = passwordEncoder.encode(cambioPasswordDTO.passwordNueva());
        cliente.setPassword(nuevaPassword);
        clienteRepo.save(cliente);
        return "El password fue cambiado con éxito";
    }

    @Override
    public Cliente registrarse(RegistroClienteDTO clienteDTO) throws Exception {

        validacionCliente.validarUnicos(clienteDTO.email().toLowerCase(), clienteDTO.nickname().toLowerCase());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(clienteDTO.password());
        Cliente nuevo = Cliente.builder()
                .email(clienteDTO.email().toLowerCase()).password(passwordEncriptada)
                .estadoRegistro(EstadoRegistro.ACTIVO).rol(Rol.builder().nombreRol(RolEnum.CLIENTE)
                        .permisos(Set.of(PermisoEnum.APROBAR, PermisoEnum.COMENTAR,
                                PermisoEnum.CALIFICAR, PermisoEnum.BUSCAR)).build())
                .nickname(clienteDTO.nickname().toLowerCase()).nombre(clienteDTO.nombre().toLowerCase())
                .ciudad(clienteDTO.ciudad().name()).fotoPerfil(clienteDTO.fotoPerfil())
                .negocios(new ArrayList<>()).favoritos(new HashSet<>())
                .recomendados(new HashSet<>()).aprobacionesComentarios(new HashSet<>()).build();
        clienteRepo.save(nuevo);
        return nuevo;
    }

    @Override
    public Cliente actualizarCliente(ActualizarClienteDTO clienteDTO) throws Exception {

        validacionCliente.validarEmail(clienteDTO.email(), clienteDTO.codigo());
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

        Optional<Cliente> buscado = clienteRepo.findByCodigo(codigoCliente);
        if (buscado.isEmpty() || buscado.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)) {
            throw new ResourceNotFoundException("No existe cliente");
        }
        Cliente cliente = buscado.get();
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
