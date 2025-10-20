package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "lugares")
public class Lugar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lugar")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_garage", nullable = false)
    private Garaje garaje;

    @Column(name = "numero_lugar")
    private Integer numeroLugar;

    @Enumerated(EnumType.STRING)
    private TipoLugar tipo;

    private Integer piso;

    @Enumerated(EnumType.STRING)
    private EstadoLugar estado;

    private Integer precio;

    @OneToMany(mappedBy = "lugar", cascade = CascadeType.ALL)
    private List<ReservaGaraje> reservas;

    // Enums
    public enum TipoLugar { auto, moto, premium }
    public enum EstadoLugar { disponible, ocupado, reservado, inactivo }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Garaje getGaraje() { return garaje; }
    public void setGaraje(Garaje garaje) { this.garaje = garaje; }

    public Integer getNumeroLugar() { return numeroLugar; }
    public void setNumeroLugar(Integer numeroLugar) { this.numeroLugar = numeroLugar; }

    public TipoLugar getTipo() { return tipo; }
    public void setTipo(TipoLugar tipo) { this.tipo = tipo; }

    public Integer getPiso() { return piso; }
    public void setPiso(Integer piso) { this.piso = piso; }

    public EstadoLugar getEstado() { return estado; }
    public void setEstado(EstadoLugar estado) { this.estado = estado; }

    public Integer getPrecio() { return precio; }
    public void setPrecio(Integer precio) { this.precio = precio; }

    public List<ReservaGaraje> getReservas() { return reservas; }
    public void setReservas(List<ReservaGaraje> reservas) { this.reservas = reservas; }
}
