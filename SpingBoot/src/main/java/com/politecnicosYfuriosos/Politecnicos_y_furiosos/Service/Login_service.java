package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Service;

import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login.ClienteRegistroDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login.Login_DTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo.Cliente;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Login_service {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteRegistroDTO login(Login_DTO request) {
        String usuarioRecibido = request.getUsuario();
        String contrasenaRecibida = request.getcontrasena();

        Optional<Cliente> clienteOpt = clienteRepository
                .findByUsuarioAndContrasena(usuarioRecibido, contrasenaRecibida);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            System.out.println("LOGIN EXITOSO para: " + cliente.getUsuario());

            // Convertir Cliente a DTO
            ClienteRegistroDTO dto = new ClienteRegistroDTO();
            dto.setNombre(cliente.getNombre());
            dto.setApellido(cliente.getApellido());
            dto.setCodigo_postal(cliente.getCodigo_postal());
            dto.setTelefono(cliente.getTelefono());
            dto.setDni(cliente.getDni());
            dto.setDireccion(cliente.getDireccion());
            dto.setUsuario(cliente.getUsuario());
            dto.setPais(cliente.getPais());
            dto.setMembresia(cliente.getMembresia());

            return dto;
        } else {
            return null;
        }
    }
}