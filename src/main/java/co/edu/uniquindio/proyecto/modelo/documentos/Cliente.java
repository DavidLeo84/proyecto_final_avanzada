package co.edu.uniquindio.proyecto.modelo.documentos;

import co.edu.uniquindio.proyecto.dtos.ActualizarClienteDTO;
import co.edu.uniquindio.proyecto.dtos.DetalleClienteDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroClienteDTO;
import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.Rol;
import co.edu.uniquindio.proyecto.modelo.Cuenta;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;


@Data
@ToString
@Document(collection = "clientes")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Cliente extends Cuenta implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private int codigo;
    private String nombre;
    private String nickname;
    private String ciudad;
    private String fotoPerfil;
    private List<String> favoritos;
    private List<String> negocios;

    public Cliente(RegistroClienteDTO clienteDTO, String password, int codigo) {
        super(clienteDTO.email(), password, EstadoRegistro.ACTIVO, Rol.USUARIO);
        this.codigo = codigo;
        this.nombre = clienteDTO.nombre();
        this.nickname = clienteDTO.nickname();
        this.ciudad = clienteDTO.ciudad();
        this.fotoPerfil = clienteDTO.fotoPerfil();
        this.favoritos = clienteDTO.favoritos();
        this.negocios = clienteDTO.negocios();
    }

    public void actualizar(DetalleClienteDTO clienteDTO) {
        this.setNombre(clienteDTO.nombre());
        this.setCiudad(clienteDTO.ciudad());
        this.setFotoPerfil(clienteDTO.fotoPerfil());
    }

}
