package co.edu.uniquindio.proyecto.controller;


import co.edu.uniquindio.proyecto.servicios.ModeradorServicioImpl;
import co.edu.uniquindio.proyecto.servicios.NegocioServicioImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moderadores")
public class ModeradorController {

    private final ModeradorServicioImpl moderadorServicio;
    private final NegocioServicioImpl negocioServicio;



}
