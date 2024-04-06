package co.edu.uniquindio.proyecto.servicios.interfaces;


import org.springframework.stereotype.Service;

@Service
public interface IEmailServicio {

    void enviarEmail(String destinatario, String asunto, String mensaje);

}
