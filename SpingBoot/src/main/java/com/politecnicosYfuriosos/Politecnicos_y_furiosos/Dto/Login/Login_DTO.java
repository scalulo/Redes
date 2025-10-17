package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Login;

public class Login_DTO {
    private String usuario;
    private String contrasena;

    public Login_DTO(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getcontrasena() {
        return contrasena;
    }

    public void setcontrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
