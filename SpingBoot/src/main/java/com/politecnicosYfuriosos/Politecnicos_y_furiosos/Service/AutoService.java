package com.rda.concesionaria.service;

import com.rda.concesionaria.dto.AutoCatalogoDTO;
import com.rda.concesionaria.dto.AutoDTO;
import com.rda.concesionaria.entity.Auto;
import com.rda.concesionaria.repository.AutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AutoService {
    
    private final AutoRepository autoRepository;
    
    /**
     * Obtener todos los autos para el catálogo
     */
    public List<AutoCatalogoDTO> obtenerTodosParaCatalogo() {
        return autoRepository.findAll()
                .stream()
                .map(AutoCatalogoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * Filtrar autos por tipo
     */
    public List<AutoCatalogoDTO> filtrarPorTipo(String tipo) {
        try {
            Auto.TipoAuto tipoAuto = Auto.TipoAuto.valueOf(tipo.toUpperCase());
            return autoRepository.findByTipo(tipoAuto)
                    .stream()
                    .map(AutoCatalogoDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de auto inválido: " + tipo);
        }
    }
    
    /**
     * Filtrar autos por disponibilidad
     */
    public List<AutoCatalogoDTO> filtrarPorDisponibilidad(Boolean disponible) {
        return autoRepository.findByDisponible(disponible)
                .stream()
                .map(AutoCatalogoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * Filtrar autos por tipo y disponibilidad
     */
    public List<AutoCatalogoDTO> filtrarPorTipoYDisponibilidad(String tipo, Boolean disponible) {
        try {
            Auto.TipoAuto tipoAuto = Auto.TipoAuto.valueOf(tipo.toUpperCase());
            return autoRepository.findByTipoAndDisponible(tipoAuto, disponible)
                    .stream()
                    .map(AutoCatalogoDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de auto inválido: " + tipo);
        }
    }
    
    /**
     * Buscar autos por marca o modelo
     */
    public List<AutoCatalogoDTO> buscarPorMarcaOModelo(String search) {
        if (search == null || search.trim().isEmpty()) {
            return obtenerTodosParaCatalogo();
        }
        
        return autoRepository.searchByMarcaOrModelo(search)
                .stream()
                .map(AutoCatalogoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtener detalle completo de un auto por ID
     */
    public AutoDTO obtenerDetallePorId(Integer id) {
        Auto auto = autoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auto no encontrado con ID: " + id));
        return AutoDTO.fromEntity(auto);
    }
    
    /**
     * Obtener autos destacados para la grilla
     */
    public List<AutoDTO> obtenerDestacados(int limit) {
        return autoRepository.findDestacados()
                .stream()
                .limit(limit)
                .map(AutoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtener el último lanzamiento (para la sección featured)
     */
    public AutoDTO obtenerUltimoLanzamiento() {
        Auto auto = autoRepository.findUltimoLanzamiento();
        if (auto == null) {
            throw new RuntimeException("No hay autos disponibles");
        }
        return AutoDTO.fromEntity(auto);
    }
    
    /**
     * Crear nuevo auto
     */
    @Transactional
    public AutoDTO crearAuto(AutoDTO autoDTO) {
        Auto auto = new Auto();
        actualizarEntidadDesdeDTO(auto, autoDTO);
        Auto autoGuardado = autoRepository.save(auto);
        return AutoDTO.fromEntity(autoGuardado);
    }
    
    /**
     * Actualizar auto existente
     */
    @Transactional
    public AutoDTO actualizarAuto(Integer id, AutoDTO autoDTO) {
        Auto auto = autoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auto no encontrado con ID: " + id));
        actualizarEntidadDesdeDTO(auto, autoDTO);
        Auto autoActualizado = autoRepository.save(auto);
        return AutoDTO.fromEntity(autoActualizado);
    }
    
    /**
     * Eliminar auto
     */
    @Transactional
    public void eliminarAuto(Integer id) {
        if (!autoRepository.existsById(id)) {
            throw new RuntimeException("Auto no encontrado con ID: " + id);
        }
        autoRepository.deleteById(id);
    }
    
    /**
     * Método auxiliar para actualizar la entidad desde el DTO
     */
    private void actualizarEntidadDesdeDTO(Auto auto, AutoDTO dto) {
        auto.setMarca(dto.getMarca());
        auto.setModelo(dto.getModelo());
        auto.setAnio(dto.getAnio());
        auto.setPrecioPorDia(dto.getPrecioPorDia());
        auto.setTipo(Auto.TipoAuto.valueOf(dto.getTipo().toUpperCase()));
        auto.setDisponible(dto.getDisponible());
        auto.setDescripcion(dto.getDescripcion());
        auto.setImagen1(dto.getImagen1());
        auto.setImagen2(dto.getImagen2());
        auto.setImagen3(dto.getImagen3());
        auto.setImagen4(dto.getImagen4());
    }
}