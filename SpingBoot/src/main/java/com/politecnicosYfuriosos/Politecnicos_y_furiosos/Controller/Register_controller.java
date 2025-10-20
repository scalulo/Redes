package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Controller;

import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login.ClienteRegistroDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login.Login_DTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login.Respuesta_login_DTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Register.RegisterResponseDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Service.Registro_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")

public class Register_controller {

    @Autowired
    private Registro_service registro;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody ClienteRegistroDTO request) {
        RegisterResponseDTO response = registro.registrar(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }

}
