package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.*;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.Rol;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceInvalidException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.interfaces.IClienteServicio;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteServicioImpl implements IClienteServicio {

    private final ClienteRepo clienteRepo;
    private final ModeradorRepo moderadorRepo;
    private final ValidacionCliente validacionCliente;
    private final EmailServicioImpl emailServicio;
    private final CloudinaryServicioImpl cloudinaryServicio;

    @Override
    public void iniciarSesion(SesionDTO sesionDTO) throws Exception {

        //invoca al servicio de JWT de crear token
        //return  token
    }

    //Metodo para eliminar la cuenta del moderador
    @Override
    public void eliminarCuenta(String codigo) throws Exception {
        try {
            Moderador moderador = validacionCliente.buscarModerador(codigo);
            moderador.setEstadoRegistro(EstadoRegistro.ELIMINADO);
            moderadorRepo.save(moderador);
        } catch (ResourceNotFoundException e) {
            e.getMessage();
        }
    }

    //Metodo pendiente por implementar en test
    @Override
    public void enviarLinkRecuperacion(String destinatario) throws Exception {

        validacionCliente.existeEmail(destinatario);
        emailServicio.enviarEmail(destinatario, "Recuperar contraseña",
                "http://localhost:8080/api/cliente/recoPass");
    }

    @Override
    public void cambiarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception {

        //return token
    }

    @Override
    public Cliente registrarse(RegistroClienteDTO clienteDTO) throws Exception {

        validacionCliente.validarUnicos(clienteDTO.email(), clienteDTO.nickname());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(clienteDTO.password());
        Cliente nuevo = Cliente.builder()
                .email(clienteDTO.email()).password(passwordEncriptada)
                .estadoRegistro(EstadoRegistro.ACTIVO).rol(Rol.USUARIO)
                .nickname(clienteDTO.nickname()).nombre(clienteDTO.nombre())
                .ciudad(clienteDTO.ciudad()).fotoPerfil(clienteDTO.fotoPerfil())
                .negocios(new ArrayList<>()).favoritos(new HashSet<>())
                .recomendados(new HashSet<>()).aprobacionesComentarios(new HashSet<>()).build();
        clienteRepo.save(nuevo);
        return nuevo;
    }

    @Override
    public Cliente editarPerfil(DetalleClienteDTO clienteDTO, String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        cliente.setNombre(clienteDTO.nombre());
        cliente.setCiudad(clienteDTO.ciudad());
        cliente.setFotoPerfil(clienteDTO.fotoPerfil());
        clienteRepo.save(cliente);
        return cliente;
    }

    //Metodo para eliminar la cuenta del cliente
    @Override
    public void eliminarPerfil(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);
        List<String> lista = validacionCliente.obtenerListadoNegociosCliente(codigoCliente);
        if (lista.isEmpty()) {
            cliente.setEstadoRegistro(EstadoRegistro.INACTIVO);
            clienteRepo.save(cliente);
        }else {
            throw new ResourceNotFoundException("Error! Hay negocios asociados que impiden eliminar la cuenta");
        }
    }

    @Override
    public DetalleClienteDTO obtenerUsuario(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);

        return new DetalleClienteDTO(
                cliente.getNombre(),
                cliente.getCiudad(),
                cliente.getFotoPerfil()
        );
    }
}
