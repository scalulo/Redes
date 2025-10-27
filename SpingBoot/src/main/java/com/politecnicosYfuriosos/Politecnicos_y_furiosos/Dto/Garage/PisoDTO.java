package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage;
// PisoDTO.java


import java.util.List;

public class PisoDTO {
    private Integer numeroPiso;
    private String nombre;
    private List<EspacioDTO> espacios;

    public PisoDTO() {}

    public PisoDTO(Integer numeroPiso, String nombre, List<EspacioDTO> espacios) {
        this.numeroPiso = numeroPiso;
        this.nombre = nombre;
        this.espacios = espacios;
    }

    public Integer getNumeroPiso() { return numeroPiso; }
    public void setNumeroPiso(Integer numeroPiso) { this.numeroPiso = numeroPiso; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<EspacioDTO> getEspacios() { return espacios; }
    public void setEspacios(List<EspacioDTO> espacios) { this.espacios = espacios; }
}