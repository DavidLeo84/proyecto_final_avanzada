package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.servicios.interfaces.IEmailServicio;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailServicioImpl implements IEmailServicio {

    @Value("${email.sender}")
    private String emailUser;

    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void enviarEmail(String destinatario, String asunto, String mensaje) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailUser);//correo electronico del remitente

        mailMessage.setTo(destinatario);//correo electronico del destinatario

        mailMessage.setSubject(asunto);//Asunto del correo

        mailMessage.setText(mensaje);//mensaje del correo que se enviará

        mailSender.send(mailMessage);
    }
}
