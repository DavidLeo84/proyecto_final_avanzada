package co.edu.uniquindio.proyecto;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.PermisoEnum;
import co.edu.uniquindio.proyecto.enums.RolEnum;
import co.edu.uniquindio.proyecto.modelo.Rol;
import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import co.edu.uniquindio.proyecto.repositorios.ModeradorRepo;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class ProyectoFinalAvanzadaApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProyectoFinalAvanzadaApplication.class, args);
    }

    /*@Bean
    CommandLineRunner init(NegocioServicioImpl negocioServicio) {

        return args -> {
            negocioServicio.calcularPromedioCalificaficaciones();
        };
    }*/

   /* @Bean
    CommandLineRunner init(ModeradorRepo moderadorRepo) {

        return args -> {


            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password_1 = passwordEncoder.encode("1234");
            String password_2 = passwordEncoder.encode("5678");
            String password_3 = passwordEncoder.encode("9012");

            Moderador moderador1 = Moderador.builder()
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .password(password_1)
                    .rol(Rol.builder().nombreRol(RolEnum.MODERADOR)
                            .permisos(Set.of(PermisoEnum.APROBAR)).build())
                    .estadoRegistro(EstadoRegistro.ACTIVO)
                    .email("mode1@correo.com")
                    .nombre("David")
                    .build();

            Moderador moderador2 = Moderador.builder()
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .password(password_2)
                    .rol(Rol.builder().nombreRol(RolEnum.MODERADOR)
                            .permisos(Set.of(PermisoEnum.APROBAR)).build())
                    .estadoRegistro(EstadoRegistro.ACTIVO)
                    .email("mode2@correo.com")
                    .nombre("Leonardo")
                    .build();

            Moderador moderador3 = Moderador.builder()
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .password(password_3)
                    .rol(Rol.builder().nombreRol(RolEnum.MODERADOR)
                            .permisos(Set.of(PermisoEnum.APROBAR)).build())
                    .estadoRegistro(EstadoRegistro.ACTIVO)
                    .email("mode3@correo.com")
                    .nombre("Ronnie")
                    .build();

            moderadorRepo.saveAll(List.of(moderador1, moderador2, moderador3));

        };
    }*/

}
