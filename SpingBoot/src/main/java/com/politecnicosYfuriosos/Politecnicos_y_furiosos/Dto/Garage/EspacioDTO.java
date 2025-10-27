package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage;
// EspacioDTO.java


public class EspacioDTO {
    private Integer id;
    private Integer numero;
    private String tipo;
    private Integer piso;
    private String estado;
    private Double precio;
    private boolean ocupado;

    public EspacioDTO() {}

    public EspacioDTO(Integer id, Integer numero, String tipo, Integer piso, String estado, Double precio, boolean ocupado) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.piso = piso;
        this.estado = estado;
        this.precio = precio;
        this.ocupado = ocupado;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getPiso() { return piso; }
    public void setPiso(Integer piso) { this.piso = piso; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public boolean isOcupado() { return ocupado; }
    public void setOcupado(boolean ocupado) { this.ocupado = ocupado; }
}