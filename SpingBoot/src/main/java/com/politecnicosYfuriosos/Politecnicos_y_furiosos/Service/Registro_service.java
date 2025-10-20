

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

    public RegisterResponseDTO registrar (ClienteRegistroDTO request) {

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
            nuevoCliente.setCodigo_postal(request.getCodigo_postal());
            // Guardar en base de datos
            Cliente clienteGuardado = clienteRepository.save(nuevoCliente);

            // Convertir a DTO
            ClienteRegistroDTO clienteDTO = convertirAClienteDTO(clienteGuardado);

            return new RegisterResponseDTO(true, "Registro exitoso. Ya puedes iniciar sesión", clienteDTO);


    }

        private ClienteRegistroDTO convertirAClienteDTO (Cliente cliente){
            ClienteRegistroDTO dto = new ClienteRegistroDTO();
            dto.setDni(cliente.getDni());
            dto.setNombre(cliente.getNombre());
            dto.setApellido(cliente.getApellido());
            dto.setPais(cliente.getPais());
            dto.setTelefono(cliente.getTelefono());
            dto.setDireccion(cliente.getDireccion());
            dto.setUsuario(cliente.getUsuario());
            dto.setCodigo_postal(cliente.getCodigo_postal());
            return dto;
        }

}
