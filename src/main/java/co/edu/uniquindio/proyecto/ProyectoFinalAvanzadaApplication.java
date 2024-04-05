package co.edu.uniquindio.proyecto;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.Rol;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class ProyectoFinalAvanzadaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoFinalAvanzadaApplication.class, args);
    }

    /*@Bean
    CommandLineRunner init(ModeradorRepo moderadorRepo) {

        return args -> {

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password_1 = passwordEncoder.encode("1234");
            String password_2 = passwordEncoder.encode("5678");
            String password_3 = passwordEncoder.encode("9012");

            Moderador moderador1 = new Moderador("mode1@correo.com", password_1,
                    EstadoRegistro.ACTIVO, Rol.ADMINISTRADOR, "1");

            Moderador moderador2 = new Moderador("mode2@correo.com", password_2,
                    EstadoRegistro.ACTIVO, Rol.ADMINISTRADOR, "2");

            Moderador moderador3 = new Moderador("mode3@correo.com", password_3,
                    EstadoRegistro.ACTIVO, Rol.ADMINISTRADOR, "3");

            moderadorRepo.saveAll(List.of(moderador1, moderador2, moderador3));
        };
    }*/

}
