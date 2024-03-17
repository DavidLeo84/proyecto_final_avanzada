package co.edu.uniquindio.proyecto.servicios.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface IComentarioServicio {

    void crearComentario() throws Exception;;

    void responderComentario() throws Exception;;

    void listarComentariosNegocio() throws Exception;;

    void calcularPromedioCalificaciones() throws Exception;;

    void darMegusta() throws Exception; //funcionalidad adicional

    void calcularCantidadMegusta() throws Exception;
}
