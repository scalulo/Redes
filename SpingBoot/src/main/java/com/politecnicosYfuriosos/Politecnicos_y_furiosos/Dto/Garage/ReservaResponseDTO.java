package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage;
// ReservaResponseDTO.java

import java.time.LocalDateTime;
import java.util.List;

public class ReservaResponseDTO {
    private boolean success;
    private String mensaje;
    private Integer reservaId;
    private Double total;
    private LocalDateTime fechaReserva;
    private List<EspacioReservaDTO> espacios;

    public ReservaResponseDTO() {}

    public ReservaResponseDTO(boolean success, String mensaje, Integer reservaId, Double total, LocalDateTime fechaReserva, List<EspacioReservaDTO> espacios) {
        this.success = success;
        this.mensaje = mensaje;
        this.reservaId = reservaId;
        this.total = total;
        this.fechaReserva = fechaReserva;
        this.espacios = espacios;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Integer getReservaId() { return reservaId; }
    public void setReservaId(Integer reservaId) { this.reservaId = reservaId; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public LocalDateTime getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDateTime fechaReserva) { this.fechaReserva = fechaReserva; }

    public List<EspacioReservaDTO> getEspacios() { return espacios; }
    public void setEspacios(List<EspacioReservaDTO> espacios) { this.espacios = espacios; }
}