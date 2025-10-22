
/**
        package com.rda.concesionaria.controller;

import com.rda.concesionaria.dto.AutoCatalogoDTO;
import com.rda.concesionaria.dto.AutoDTO;
import com.rda.concesionaria.service.AutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Configurar según tus necesidades
public class AutoController {
    
    private final AutoService autoService;
    

    @GetMapping("/catalogo")
    public ResponseEntity<List<AutoCatalogoDTO>> obtenerCatalogo(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Boolean disponible,
            @RequestParam(required = false) String search) {
        
        List<AutoCatalogoDTO> autos;
        
        // Si hay búsqueda, priorizar eso
        if (search != null && !search.trim().isEmpty()) {
            autos = autoService.buscarPorMarcaOModelo(search);
        }
        // Si hay tipo y disponibilidad
        else if (tipo != null && disponible != null) {
            autos = autoService.filtrarPorTipoYDisponibilidad(tipo, disponible);
        }
        // Si solo hay tipo
        else if (tipo != null) {
            autos = autoService.filtrarPorTipo(tipo);
        }
        // Si solo hay disponibilidad
        else if (disponible != null) {
            autos = autoService.filtrarPorDisponibilidad(disponible);
        }
        // Sin filtros, devolver todos
        else {
            autos = autoService.obtenerTodosParaCatalogo();
        }
        
        return ResponseEntity.ok(autos);
    }
    

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<AutoCatalogoDTO>> filtrarPorTipo(@PathVariable String tipo) {
        List<AutoCatalogoDTO> autos = autoService.filtrarPorTipo(tipo);
        return ResponseEntity.ok(autos);
    }
    

    @GetMapping("/disponibles")
    public ResponseEntity<List<AutoCatalogoDTO>> obtenerDisponibles() {
        List<AutoCatalogoDTO> autos = autoService.filtrarPorDisponibilidad(true);
        return ResponseEntity.ok(autos);
    }
    

    @GetMapping("/destacados")
    public ResponseEntity<List<AutoDTO>> obtenerDestacados(
            @RequestParam(defaultValue = "4") int limit) {
        List<AutoDTO> autos = autoService.obtenerDestacados(limit);
        return ResponseEntity.ok(autos);
    }
    

    @GetMapping("/ultimo-lanzamiento")
    public ResponseEntity<AutoDTO> obtenerUltimoLanzamiento() {
        AutoDTO auto = autoService.obtenerUltimoLanzamiento();
        return ResponseEntity.ok(auto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutoDTO> obtenerDetalle(@PathVariable Integer id) {
        AutoDTO auto = autoService.obtenerDetallePorId(id);
        return ResponseEntity.ok(auto);
    }
    

    @GetMapping("/buscar")
    public ResponseEntity<List<AutoCatalogoDTO>> buscar(
            @RequestParam String q) {
        List<AutoCatalogoDTO> autos = autoService.buscarPorMarcaOModelo(q);
        return ResponseEntity.ok(autos);
    }
    

    @PostMapping
    public ResponseEntity<AutoDTO> crearAuto(@RequestBody AutoDTO autoDTO) {
        AutoDTO nuevoAuto = autoService.crearAuto(autoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAuto);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<AutoDTO> actualizarAuto(
            @PathVariable Integer id,
            @RequestBody AutoDTO autoDTO) {
        AutoDTO autoActualizado = autoService.actualizarAuto(id, autoDTO);
        return ResponseEntity.ok(autoActualizado);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAuto(@PathVariable Integer id) {
        autoService.eliminarAuto(id);
        return ResponseEntity.noContent().build();
    }
}
 **/