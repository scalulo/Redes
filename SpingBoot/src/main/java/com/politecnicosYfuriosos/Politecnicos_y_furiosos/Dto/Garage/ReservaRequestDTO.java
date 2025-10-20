package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage;



import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservaRequestDTO {
    private List<Integer> espaciosIds;
    private LocalDate fechaInicio;
    private String duracion;
    private LocalTime horaInicio;

    public List<Integer> getEspaciosIds() { return espaciosIds; }
    public void setEspaciosIds(List<Integer> espaciosIds) { this.espaciosIds = espaciosIds; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
}