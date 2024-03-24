package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dtos.ActualizarNegocioDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroHistorialDTO;
import co.edu.uniquindio.proyecto.dtos.RegistroNegocioDTO;
import co.edu.uniquindio.proyecto.servicios.interfaces.INegocioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class NegocioServicioImpl implements INegocioServicio {


    @Override
    public void crearNegocio(RegistroNegocioDTO registroNegocioDTO) throws Exception {

    }

    @Override
    public void actualizarNegocio(ActualizarNegocioDTO negocioDTO) throws Exception {

    }

    @Override
    public void eliminarNegocio(String idNegocio) throws Exception {

    }

    @Override
    public void buscarNegocios(String codigo) throws Exception {

    }

    @Override
    public void filtrarPorEstado(String estado) throws Exception {

    }

    @Override
    public void listarNegociosPropietario(String idUsuario) throws Exception {

    }

    @Override
    public void cambiarEstado(String idNegocio) throws Exception {

    }

    @Override
    public void registrarRevision(RegistroHistorialDTO historialDTO) throws Exception {

    }

    @Override
    public void obtenerRecomendados() throws Exception {

    }

    @Override
    public void guardarNegocioFavorito() throws Exception {

    }

    @Override
    public void guardarRecomendados() throws Exception {

    }
}
