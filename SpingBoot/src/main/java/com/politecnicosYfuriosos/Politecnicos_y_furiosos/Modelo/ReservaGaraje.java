package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservasGaraje")
public class ReservaGaraje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Integer id;

    // ✅ RELACIÓN CORRECTA
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_lugar", nullable = false)
    private Lugar lugar;

    @Column(name = "fecha_reserva")
    private LocalDateTime fechaReserva;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    private Integer duracion;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;

    public enum EstadoReserva { pendiente, activa, finalizada, cancelada }

    // Constructores
    public ReservaGaraje() {}

    public ReservaGaraje(Cliente cliente, Lugar lugar, LocalDateTime fechaReserva,
                         LocalDateTime fechaInicio, LocalDateTime fechaFin,
                         Integer duracion, EstadoReserva estado) {
        this.cliente = cliente;
        this.lugar = lugar;
        this.fechaReserva = fechaReserva;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.duracion = duracion;
        this.estado = estado;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Lugar getLugar() { return lugar; }
    public void setLugar(Lugar lugar) { this.lugar = lugar; }

    public LocalDateTime getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDateTime fechaReserva) { this.fechaReserva = fechaReserva; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    public EstadoReserva getEstado() { return estado; }
    public void setEstado(EstadoReserva estado) { this.estado = estado; }

    // ✅ MÉTODO HELPER para obtener el ID del cliente
    public Integer getClienteId() {
        return cliente != null ? cliente.getId() : null;
    }
}