package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage;


public class EspacioReservaDTO {
    private Integer id;
    private Integer numero;
    private String tipo;
    private Integer piso;
    private Double precio;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getPiso() { return piso; }
    public void setPiso(Integer piso) { this.piso = piso; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
}