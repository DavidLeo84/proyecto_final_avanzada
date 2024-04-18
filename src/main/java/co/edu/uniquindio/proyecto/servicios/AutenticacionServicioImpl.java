package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.AuthResponse;
import co.edu.uniquindio.proyecto.dtos.TokenDTO;
import co.edu.uniquindio.proyecto.dtos.LoginDTO;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionModerador;
import co.edu.uniquindio.proyecto.servicios.interfaces.IAutenticacionServicio;
import co.edu.uniquindio.proyecto.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AutenticacionServicioImpl implements IAutenticacionServicio {

    private final ClienteRepo clienteRepo;
    private final ModeradorRepo moderadorRepo;
    private final JWTUtils jwtUtils;


    @Autowired
    private ValidacionCliente validacionCliente;
    @Autowired
    private ValidacionModerador validacionModerador;

    @Override
    public TokenDTO iniciarSesionCliente(LoginDTO loginDTO) throws Exception {

        Cliente cliente = validacionCliente.buscarPorEmail(loginDTO.email());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginDTO.password(), cliente.getPassword())) {
            throw new Exception("La contraseña es incorrecta");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rol", "CLIENTE");
        map.put("nombre", cliente.getNombre());
        map.put("id", cliente.getCodigo());
        return new TokenDTO(jwtUtils.generarToken(cliente.getEmail(), map));
    }

    @Override
    public TokenDTO iniciarSesionModerador(LoginDTO loginDTO) throws Exception {
        Moderador moderador = validacionModerador.buscarPorEmail(loginDTO.email());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginDTO.password(), moderador.getPassword())) {
            throw new Exception("La contraseña es incorrecta");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rol", "MODERADOR");
        map.put("nombre", moderador.getNombre());
        map.put("id", moderador.getCodigo());
        return new TokenDTO(jwtUtils.generarToken(moderador.getEmail(), map));
    }

    /*public TokenDTO validarPassword(String codigoCliente) throws Exception {

        Cliente cliente = validacionCliente.buscarCliente(codigoCliente);

        Map<String, Object> map = new HashMap<>();
        map.put("rolEnum", "CLIENTE");
        map.put("nombre", cliente.getNombre());
        map.put("id", cliente.getCodigo());
        return new TokenDTO(jwtUtils.generarToken(cliente.getEmail(), map));
    }*/

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

