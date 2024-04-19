package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dtos.ImagenDTO;
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
@RequestMapping("/api/clientes/imagenes")
public class ImagenController {

    private final CloudinaryServicioImpl cloudinaryServicio;

    @PostMapping("/subir-imagen")
    public ResponseEntity<?> subirImagen(@RequestParam MultipartFile imagen) throws Exception {

        BufferedImage bi = ImageIO.read(imagen.getInputStream());
        if (bi == null) {
            return new ResponseEntity(new MensajeDTO(false,"imagen no valida"), HttpStatus.BAD_REQUEST);
        }
        Map resultado = cloudinaryServicio.subirImagen(imagen);
        //return new ResponseEntity(resultado, HttpStatus.OK);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, resultado));
    }

    @PostMapping("/eliminar-imagen")
    public ResponseEntity<?> eliminarImagen(@RequestBody ImagenDTO imagenDTO) throws Exception {

        Map resultado = cloudinaryServicio.eliminarImagen(imagenDTO.id());
        //return new ResponseEntity(resultado, HttpStatus.OK);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, resultado));
    }

}
