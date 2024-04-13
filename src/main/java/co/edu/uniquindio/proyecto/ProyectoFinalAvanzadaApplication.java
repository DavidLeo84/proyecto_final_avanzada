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

            Moderador moderador1 = Moderador.builder()
                    .password(password_1)
                    .rol(Rol.MODERADOR)
                    .estadoRegistro(EstadoRegistro.ACTIVO)
                    .email("mode1@correo.com")
                    .nombre("David")
                    .build();

            Moderador moderador2 = Moderador.builder()
                    .password(password_2)
                    .rol(Rol.MODERADOR)
                    .estadoRegistro(EstadoRegistro.ACTIVO)
                    .email("mode2@correo.com")
                    .nombre("Leonardo")
                    .build();

            Moderador moderador3 = Moderador.builder()
                    .password(password_3)
                    .rol(Rol.MODERADOR)
                    .estadoRegistro(EstadoRegistro.ACTIVO)
                    .email("mode3@correo.com")
                    .nombre("Ronnie")
                    .build();

            moderadorRepo.saveAll(List.of(moderador1, moderador2, moderador3));
        };
    }*/

}
