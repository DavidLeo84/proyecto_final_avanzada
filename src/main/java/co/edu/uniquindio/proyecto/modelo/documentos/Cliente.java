package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.enums.CiudadEnum;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.RolEnum;
import co.edu.uniquindio.proyecto.modelo.Cuenta;
import co.edu.uniquindio.proyecto.modelo.Rol;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = "clientes")
@EqualsAndHashCode(callSuper = false)
public class Cliente extends Cuenta implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private String nickname;
    private String ciudad;
    private String fotoPerfil;
    private Set<String> favoritos;
    private List<String> negocios;
    private Set<String> recomendados; //funcionalidad adicional
    private Set<String> aprobacionesComentarios;


    @Builder
    public Cliente(String email, String password, EstadoRegistro estadoRegistro, Rol rol, String nombre,
                   String codigo, String nickname, String ciudad, String fotoPerfil,
                   Set<String> favoritos, List<String> negocios,
                   Set<String> recomendados, Set<String> aprobacionesComentarios) {
        super(email, password, estadoRegistro, rol, nombre);
        this.codigo = codigo;
        this.nickname = nickname;
        this.ciudad = ciudad;
        this.fotoPerfil = fotoPerfil;
        this.favoritos = favoritos;
        this.negocios = negocios;
        this.recomendados = recomendados;
        this.aprobacionesComentarios = aprobacionesComentarios;
    }
}
