package com.politecnicosYfuriosos.Politecnicos_y_furiosos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controlador {
    @GetMapping("/hola")
    public String saludar() {
        return "¡Hola desde Spring Boot!";
    }
}

