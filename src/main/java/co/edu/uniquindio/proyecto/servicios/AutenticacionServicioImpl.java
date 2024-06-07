package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.CambioPasswordDTO;
import co.edu.uniquindio.proyecto.dtos.TokenDTO;
import co.edu.uniquindio.proyecto.dtos.LoginDTO;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;

import co.edu.uniquindio.proyecto.servicios.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionModerador;
import co.edu.uniquindio.proyecto.servicios.interfaces.IAutenticacionServicio;
import co.edu.uniquindio.proyecto.servicios.interfaces.ICuentaServicio;
import co.edu.uniquindio.proyecto.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AutenticacionServicioImpl implements IAutenticacionServicio {

    private final ClienteRepo clienteRepo;
    private final ModeradorRepo moderadorRepo;
    private final JWTUtils jwtUtils;
    private final EmailServicioImpl emailServicio;

    @Autowired
    private ValidacionCliente validacionCliente;
    @Autowired
    private ValidacionModerador validacionModerador;


    @Override
    public TokenDTO iniciarSesionCliente(LoginDTO loginDTO) throws Exception {

        Cliente cliente = Cliente.builder().build();
        Map<String, Object> map = new HashMap<>();
        try {
            cliente = validacionCliente.buscarPorEmail(loginDTO.email());
        } catch (Exception e) {
            System.out.println("MensajeError = " + e.getMessage());
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginDTO.password(), cliente.getPassword())) {
            throw new NoSuchElementException("La contraseña es incorrecta");
        }
        map.put("rol", "CLIENTE");
        map.put("nombre", cliente.getNombre());
        map.put("id", cliente.getCodigo());
        return new TokenDTO(jwtUtils.generarToken(cliente.getEmail(), map));
    }

    @Override
    public TokenDTO iniciarSesionModerador(LoginDTO loginDTO) throws Exception {

        Moderador moderador = Moderador.builder().build();
        Map<String, Object> map = new HashMap<>();
        try {
            moderador = validacionModerador.buscarPorEmail(loginDTO.email());
        } catch (Exception e) {
            System.out.println("MensajeError = " + e.getMessage());
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginDTO.password(), moderador.getPassword())) {
            throw new NoSuchElementException("La contraseña es incorrecta");
        }
        map.put("rol", "MODERADOR");
        map.put("nombre", moderador.getNombre());
        map.put("id", moderador.getCodigo());
        return new TokenDTO(jwtUtils.generarToken(moderador.getEmail(), map));
    }

    public TokenDTO recuperarPasswordCliente(String email) throws Exception {

        Cliente cliente = validacionCliente.buscarPorEmail(email);

        Map<String, Object> map = new HashMap<>();
        map.put("rol", "CLIENTE");
        map.put("nickname", cliente.getNickname());
        map.put("id", cliente.getCodigo());

        return new TokenDTO(jwtUtils.generarToken(cliente.getEmail(), map));
    }

    public TokenDTO recuperarPasswordModerador(String email) throws Exception {

        Moderador moderador = validacionModerador.buscarPorEmail(email);

        Map<String, Object> map = new HashMap<>();
        map.put("rol", "MODERADOR");
        map.put("nombre", moderador.getNombre());
        map.put("id", moderador.getCodigo());

        return new TokenDTO(jwtUtils.generarToken(moderador.getEmail(), map));
    }

}

