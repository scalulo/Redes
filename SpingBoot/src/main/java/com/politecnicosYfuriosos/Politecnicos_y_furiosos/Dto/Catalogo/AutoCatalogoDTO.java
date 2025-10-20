package com.rda.concesionaria.dto;

import com.rda.concesionaria.entity.Auto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO simplificado para el catálogo principal
 * Solo incluye la información esencial para mostrar en las tarjetas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoCatalogoDTO {
    
    private Integer id;
    private String marca;
    private String modelo;
    private Integer anio;
    private BigDecimal precioPorDia;
    private String tipo;
    private Boolean disponible;
    private String imagenPrincipal; // Solo la primera imagen
    
    public static AutoCatalogoDTO fromEntity(Auto auto) {
        return AutoCatalogoDTO.builder()
                .id(auto.getId())
                .marca(auto.getMarca())
                .modelo(auto.getModelo())
                .anio(auto.getAnio())
                .precioPorDia(auto.getPrecioPorDia())
                .tipo(auto.getTipo().name())
                .disponible(auto.getDisponible())
                .imagenPrincipal(auto.getImagen1())
                .build();
    }
}