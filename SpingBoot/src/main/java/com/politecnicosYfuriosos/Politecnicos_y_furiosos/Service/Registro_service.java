package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Service;

import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login.ClienteRegistroDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Register.RegisterResponseDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo.Cliente;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Registro_service {

    @Autowired
    private ClienteRepository clienteRepository;

    public RegisterResponseDTO registrar (ClienteRegistroDTO request){

        // Validar que no exista el usuario
        if (clienteRepository.existsByUsuario(request.getUsuario())) {
            return new RegisterResponseDTO(false, "El nombre de usuario ya está en uso", null);
        }


        // Validar campos obligatorios
        if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
            return new RegisterResponseDTO(false, "El nombre es obligatorio", null);
        }

        if (request.getApellido() == null || request.getApellido().trim().isEmpty()) {
            return new RegisterResponseDTO(false, "El apellido es obligatorio", null);
        }



        if (request.getUsuario() == null || request.getUsuario().trim().isEmpty()) {
            return new RegisterResponseDTO(false, "El usuario es obligatorio", null);
        }

        if (request.getContrasena() == null || request.getContrasena().length() < 6) {
            return new RegisterResponseDTO(false, "La contraseña debe tener al menos 6 caracteres", null);
        }

        // Crear nuevo cliente
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(request.getNombre().trim());
        nuevoCliente.setApellido(request.getApellido().trim());
        nuevoCliente.setPais(request.getPais());
        nuevoCliente.setTelefono(request.getTelefono());
        nuevoCliente.setDni(request.getDni());
        nuevoCliente.setDireccion(request.getDireccion());
        nuevoCliente.setUsuario(request.getUsuario().trim());
        nuevoCliente.setContrasena(request.getContrasena());

        // Guardar en base de datos
        Cliente clienteGuardado = clienteRepository.save(nuevoCliente);

        // Convertir a DTO
        ClienteRegistroDTO clienteDTO = convertirAClienteDTO(clienteGuardado);

        return new RegisterResponseDTO(true, "Registro exitoso. Ya puedes iniciar sesión", clienteDTO);
    }

    private ClienteRegistroDTO convertirAClienteDTO(Cliente cliente) {
        ClienteRegistroDTO dto = new ClienteRegistroDTO();
        dto.setDni(cliente.getDni());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setPais(cliente.getPais());
        dto.setTelefono(cliente.getTelefono());
        dto.setDni(cliente.getDni());
        dto.setDireccion(cliente.getDireccion());
        dto.setUsuario(cliente.getUsuario());
        dto.setCodigo_postal(cliente.getCodigo_postal());
        return dto;
    }

}
