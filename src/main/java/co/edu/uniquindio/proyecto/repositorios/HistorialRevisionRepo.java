package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.HistorialRevision;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistorialRevisionRepo extends MongoRepository<HistorialRevision, String> {
}
