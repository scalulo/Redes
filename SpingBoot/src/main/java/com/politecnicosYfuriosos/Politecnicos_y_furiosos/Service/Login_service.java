package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Service;

import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login.ClienteRegistroDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login.Login_DTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login.Respuesta_login_DTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo.Cliente;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Login_service {

    @Autowired
    private ClienteRepository clienteRepository;

    public Respuesta_login_DTO login(Login_DTO request) {
        String usuarioRecibido = request.getUsuario();
        String contrasenaRecibida = request.getcontrasena();

        Optional<Cliente> clienteOpt = clienteRepository
                .findByUsuarioAndContrasena(usuarioRecibido, contrasenaRecibida);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            System.out.println("LOGIN EXITOSO para: " + cliente.getUsuario());
            return new Respuesta_login_DTO(true, "Inicio de sesión exitoso", null);
        } else {
            return new Respuesta_login_DTO(false, "Usuario o contraseña incorrectos", null);
        }
    }

 
}