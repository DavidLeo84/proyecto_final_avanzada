package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeradorRepo extends MongoRepository<Moderador, String> {

    Optional<Moderador> findByCodigo(String codigo);
}
