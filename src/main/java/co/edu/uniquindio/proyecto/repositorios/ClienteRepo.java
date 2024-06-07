package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepo extends MongoRepository<Cliente, String> {

    Optional<Cliente> findByNickname(String nickname) throws Exception;
    Optional<Cliente> findByCodigo(String codigoCliente) throws Exception;
    Optional<Cliente> findByEmail(String email) throws Exception;
    List<Cliente> findAllByEmail(String email) throws Exception;

}
