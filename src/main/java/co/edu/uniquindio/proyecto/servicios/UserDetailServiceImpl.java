package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.enums.RolEnum;
import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.ValidacionCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final ClienteRepo clienteRepo;
    @Autowired
    private ValidacionCliente validacionCliente;

    @Override
    public UserDetails loadUserByUsername(String email) {
        try {
            Cliente cliente = validacionCliente.buscarPorEmail(email);
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

            cliente.getRol().getPermisos().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.name()))));
            cliente.getRol().getPermisos().forEach(permisoEnum -> authorityList.add(new SimpleGrantedAuthority(permisoEnum.name())));
            //cliente.getRol().getPermisos().stream().map(permisoEnum -> authorityList.add(new SimpleGrantedAuthority(permisoEnum.name())));
            return new User(cliente.getEmail(),
                    cliente.getPassword(),
                    cliente.isEnabled(),
                    cliente.isAccountNoExpired(),
                    cliente.isAccountNoExpired(),
                    cliente.isAccountNoLocked(),
                    authorityList
            );
        } catch (Exception e) {
            try {
                throw new Exception("No existe el cliente");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
