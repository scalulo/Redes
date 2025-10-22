package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login;

import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo.Cliente;

import java.util.Optional;

public class Respuesta_login_DTO {

    private boolean success;
    private String mensaje;
    private ClienteRegistroDTO cliente;

    // Constructores
    public Respuesta_login_DTO() {}

    public Respuesta_login_DTO(boolean success, String mensaje, ClienteRegistroDTO cliente) {
        this.success = success;
        this.mensaje = mensaje;
        this.cliente = cliente;
    }

    public Respuesta_login_DTO(boolean success, String inicioDeSesiónExitoso, Optional<Cliente> clienteOpt) {
    }

    // Getters y Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public ClienteRegistroDTO getCliente() { return cliente; }
    public void setCliente(ClienteRegistroDTO cliente) { this.cliente = cliente; }
}
