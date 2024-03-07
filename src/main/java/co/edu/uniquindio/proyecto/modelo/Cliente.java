package co.edu.uniquindio.proyecto.modelo;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.lang.annotation.Inherited;
import java.util.List;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Document(collection = "clientes")
public class Cliente extends Cuenta implements Serializable{

    @Id
    @EqualsAndHashCode.Include
    private String cedula;
    private String ciudad;
    private String nickname;
    private String fotoPerfil;
    private List<Favorito> misFavoritos;
    private List<Negocio> negocios;

    @Builder
    public Cliente(String nombre, String password, String email, EstadoRegistro estadoRegistro,
                   String cedula, String ciudad, String nickname, String fotoPerfil,
                   List<Favorito> misFavoritos, List<Negocio> negocios)
    {
        super(nombre, password, email, estadoRegistro);
        this.cedula = cedula;
        this.ciudad = ciudad;
        this.nickname = nickname;
        this.fotoPerfil = fotoPerfil;
        this.misFavoritos = misFavoritos;
        this.negocios = negocios;
    }
}
