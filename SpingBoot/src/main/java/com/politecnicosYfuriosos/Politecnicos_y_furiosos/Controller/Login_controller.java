package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Controller;

import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login.Login_DTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login.Respuesta_login_DTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Service.Login_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class Login_controller {

    @Autowired
    private Login_service Login_service;

    @PostMapping("/login")
    public ResponseEntity<Respuesta_login_DTO> login(@RequestBody Login_DTO request) {
        Respuesta_login_DTO response = Login_service.login(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }
}