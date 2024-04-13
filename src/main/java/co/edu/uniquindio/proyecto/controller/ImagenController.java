package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dtos.MensajeDTO;
import co.edu.uniquindio.proyecto.servicios.CloudinaryServicioImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Map;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/imagen")
public class ImagenController {

    private final CloudinaryServicioImpl cloudinaryServicio;

    @PostMapping("/subir")
    public ResponseEntity<?> subirImagen(@RequestParam MultipartFile imagen) throws Exception {

        BufferedImage bi = ImageIO.read(imagen.getInputStream());
        if (bi == null) {
            return new ResponseEntity(new MensajeDTO(false,"imagen no valida"), HttpStatus.BAD_REQUEST);
        }
        Map resultado = cloudinaryServicio.subirImagen(imagen);
        return new ResponseEntity(resultado, HttpStatus.OK);
    }

    @PostMapping("/eliminar/{idImagen}")
    public ResponseEntity<?> eliminarImagen(@PathVariable("idImagen") String idImagen) throws Exception {

        Map resultado = cloudinaryServicio.eliminarImagen(idImagen);
        return new ResponseEntity(resultado, HttpStatus.OK);
    }

}
