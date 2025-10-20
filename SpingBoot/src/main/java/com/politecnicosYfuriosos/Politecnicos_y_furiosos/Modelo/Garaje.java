package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "garages")
public class Garaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_garage")
    private Integer id;

    private String nombre;
    private String ubicacion;

    @Column(name = "cantidad_lugares")
    private Integer cantidadLugares;

    private Integer pisos;

    @OneToMany(mappedBy = "garaje", cascade = CascadeType.ALL)
    private List<Lugar> lugares;

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public Integer getCantidadLugares() { return cantidadLugares; }
    public void setCantidadLugares(Integer cantidadLugares) { this.cantidadLugares = cantidadLugares; }

    public Integer getPisos() { return pisos; }
    public void setPisos(Integer pisos) { this.pisos = pisos; }

    public List<Lugar> getLugares() { return lugares; }
    public void setLugares(List<Lugar> lugares) { this.lugares = lugares; }
}
