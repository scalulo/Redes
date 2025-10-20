package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo;


import jakarta.persistence.*;
import java.util.List;

@Entity
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String marca;
    private String modelo;
    private int anio;
    private double precioPorDia;

    @Enumerated(EnumType.STRING)
    private TipoAuto tipo;

    private boolean disponible;
    private String descripcion;
    private String imagen1;
    private String imagen2;
    private String imagen3;
    private String imagen4;

    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL)
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL)
    private List<Resenia> resenias;

    // Getters y Setters
    // ...

    public enum TipoAuto {
        DEPORTIVO, SUV, SEDAN, CLASICO, COUPE, CONVERTIBLE
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecioPorDia() {
        return precioPorDia;
    }

    public void setPrecioPorDia(double precioPorDia) {
        this.precioPorDia = precioPorDia;
    }

    public TipoAuto getTipo() {
        return tipo;
    }

    public void setTipo(TipoAuto tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen1() {
        return imagen1;
    }

    public void setImagen1(String imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen2() {
        return imagen2;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }
    public String getImagen3() {
        return imagen3;
    }

    public void setImagen3(String imagen3) {
        this.imagen3 = imagen3;
    }
    public String getImagen4() {
        return imagen4;
    }

    public void setImagen4(String imagen4) {
        this.imagen4 = imagen4;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Resenia> getResenias() {
        return resenias;
    }

    public void setResenias(List<Resenia> resenias) {
        this.resenias = resenias;
    }
}
