package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.documentos.Moderador;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ModeradorRepo extends MongoRepository<Moderador, Integer> {
}
