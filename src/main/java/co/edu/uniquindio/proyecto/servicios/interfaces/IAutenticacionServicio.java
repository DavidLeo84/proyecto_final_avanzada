package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dtos.LoginDTO;
import co.edu.uniquindio.proyecto.dtos.TokenDTO;
import org.springframework.stereotype.Service;

@Service
public interface IAutenticacionServicio {

    TokenDTO iniciarSesionCliente(LoginDTO loginDTO)throws Exception;

    TokenDTO iniciarSesionModerador(LoginDTO loginDTO)throws Exception;

    TokenDTO recuperarPasswordCliente(String email) throws Exception;

    TokenDTO recuperarPasswordModerador(String email) throws Exception;
}
