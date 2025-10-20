package com.rda.concesionaria.dto;

import com.rda.concesionaria.entity.Auto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoDTO {
    
    private Integer id;
    private String marca;
    private String modelo;
    private Integer anio;
    private BigDecimal precioPorDia;
    private String tipo;
    private Boolean disponible;
    private String descripcion;
    private String imagen1;
    private String imagen2;
    private String imagen3;
    private String imagen4;
    
    // Constructor desde entidad
    public static AutoDTO fromEntity(Auto auto) {
        return AutoDTO.builder()
                .id(auto.getId())
                .marca(auto.getMarca())
                .modelo(auto.getModelo())
                .anio(auto.getAnio())
                .precioPorDia(auto.getPrecioPorDia())
                .tipo(auto.getTipo().name())
                .disponible(auto.getDisponible())
                .descripcion(auto.getDescripcion())
                .imagen1(auto.getImagen1())
                .imagen2(auto.getImagen2())
                .imagen3(auto.getImagen3())
                .imagen4(auto.getImagen4())
                .build();
    }
}