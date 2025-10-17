package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Register;

import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login.ClienteRegistroDTO;

public class RegisterResponseDTO {
    private boolean success;
    private String mensaje;
    private ClienteRegistroDTO cliente;

    // Constructores
    public RegisterResponseDTO() {}

    public RegisterResponseDTO(boolean success, String mensaje, ClienteRegistroDTO cliente) {
        this.success = success;
        this.mensaje = mensaje;
        this.cliente = cliente;
    }

    // Getters y Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public ClienteRegistroDTO getCliente() { return cliente; }
    public void setCliente(ClienteRegistroDTO cliente) { this.cliente = cliente; }
}

