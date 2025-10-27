package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage;
// GarageDTO.java


import java.util.List;

public class GarageDTO {
    private Integer id;
    private String nombre;
    private String ubicacion;
    private Integer pisos;
    private List<PisoDTO> pisosDetalle;

    public GarageDTO() {}

    public GarageDTO(Integer id, String nombre, String ubicacion, Integer pisos, List<PisoDTO> pisosDetalle) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.pisos = pisos;
        this.pisosDetalle = pisosDetalle;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public Integer getPisos() { return pisos; }
    public void setPisos(Integer pisos) { this.pisos = pisos; }

    public List<PisoDTO> getPisosDetalle() { return pisosDetalle; }
    public void setPisosDetalle(List<PisoDTO> pisosDetalle) { this.pisosDetalle = pisosDetalle; }
}