package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.modelo.documentos.Negocio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface NegocioRepo extends MongoRepository<Negocio, String> {

    //proyecto> db.negocios.find({ubicacion:{"latitud":123456789,"longitud":987654321}})
    @Query("{ubicacion:{latitud:?0,longitud:?1}}")
    Optional<Negocio> getNegocioByUbicacion(int latitud, int longitud) throws Exception;

    Optional<Negocio> findByCodigo(String codigo) throws Exception;

    Set<Negocio> findAllByEstadoRegistro(EstadoRegistro estadoRegistro) throws Exception;

    Set<Negocio> findAllByCodigoCliente(String codigo) throws Exception;


}
