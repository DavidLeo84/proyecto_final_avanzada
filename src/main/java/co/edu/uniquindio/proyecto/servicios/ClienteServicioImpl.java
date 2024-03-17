package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.*;

import co.edu.uniquindio.proyecto.modelo.documentos.Cliente;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.servicios.excepciones.Validacion;
import co.edu.uniquindio.proyecto.servicios.interfaces.IClienteServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
@Transactional
public class ClienteServicioImpl implements IClienteServicio {

    @Autowired
    private ClienteRepo clienteRepo;
    @Autowired
    private Validacion validacion;


    @Override
    public void iniciarSesion(SesionDTO sesionDTO) throws Exception {

    }

    @Override
    public void eliminarCuenta(String codigo) throws Exception {

    }

    @Override
    public void enviarLinkRecuperacion(String email) throws Exception {

    }

    @Override
    public void cambiarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception {

    }

    @Override
    public int registrarse(RegistroClienteDTO clienteDTO) throws Exception {

        int codigo = autoIncrementarCodigo();
        validacion.existeCliente(clienteDTO.nickname());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(clienteDTO.password());
        Cliente cliente = clienteRepo.save(new Cliente(clienteDTO, passwordEncriptada, codigo));
        return cliente.getCodigo();
    }

    @Override
    public DetalleClienteDTO editarPerfil(DetalleClienteDTO clienteDTO, int codigo) throws Exception {

        validacion.existeEmail(clienteDTO.email());
        Optional<Cliente> clienteOptional = clienteRepo.findByCodigo(codigo);
        Cliente clienteActualizado = clienteOptional.get();
        clienteActualizado.actualizar(clienteDTO);
        clienteRepo.save(clienteActualizado);
        return clienteActualizado;
    }


    @Override
    public void eliminarPerfil(String codigo) throws Exception {

    }

    @Override
    public void obtenerUsuario(String codigo) throws Exception {




    }

    @Override
    public void recuperarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception {

    }

    private int autoIncrementarCodigo() {
        List<Cliente> clientes = clienteRepo.findAll();
        return clientes.isEmpty() ? 1 :
                clientes.stream().max(Comparator.comparing(Cliente::getCodigo)).get().getCodigo() + 1;
    }




}
