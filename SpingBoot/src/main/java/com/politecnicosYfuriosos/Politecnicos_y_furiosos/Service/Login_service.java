package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Service;

import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.ClienteRegistroDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login_DTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Respuesta_login_DTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo.Cliente;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
            ClienteRegistroDTO clienteRegistroDTO = convertirAClienteRegistroDTO(cliente);
            return new Respuesta_login_DTO(true, "Inicio de sesión exitoso", clienteRegistroDTO);
        } else {
            System.out.println("LOGIN FALLIDO - No se encontró cliente");
            return new Respuesta_login_DTO(false, "Usuario o contraseña incorrectos", null);
        }
    }

    private ClienteRegistroDTO convertirAClienteRegistroDTO(Cliente cliente) {
        ClienteRegistroDTO dto = new ClienteRegistroDTO();
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setDni(cliente.getDni());
        dto.setDireccion(cliente.getDireccion());
        dto.setUsuario(cliente.getUsuario());
        return dto;
    }
}