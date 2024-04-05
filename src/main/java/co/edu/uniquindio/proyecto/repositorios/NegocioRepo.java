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

    @Query("{ubicacion:{latitud:?0,longitud:?1}}")
    List<Negocio> getListNegocioByUbicacion(int latitud, int longitud) throws Exception;

    Optional<Negocio> findByCodigo(String codigo) throws Exception;

    List<Negocio> findAllByEstadoRegistro(EstadoRegistro estadoRegistro) throws Exception;

    Set<Negocio> findAllByCodigoCliente(String codigo) throws Exception;

    @Query("{estadoRegistro:INACTIVO},{historialRevisiones:{$elemMatch:{estadoNegocio:?0}}}")
    List<Negocio> getListNegociosInactivos(String estadoNegocio) throws Exception;

    @Query("{estadoRegistro:ELIMINADO},{historialRevisiones:{$elemMatch:{estadoNegocio:?0}}}")
    List<Negocio> getListNegociosEliminados(String estadoNegocio) throws Exception;

}